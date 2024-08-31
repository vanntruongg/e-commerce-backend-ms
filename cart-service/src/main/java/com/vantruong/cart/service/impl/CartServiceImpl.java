package com.vantruong.cart.service.impl;

import com.vantruong.cart.constant.MessageConstant;
import com.vantruong.cart.dto.request.AddToCartRequest;
import com.vantruong.cart.dto.request.DeleteCartRequest;
import com.vantruong.cart.dto.request.UpdateCartRequest;
import com.vantruong.cart.dto.response.CartItemResponse;
import com.vantruong.cart.dto.response.CartResponse;
import com.vantruong.cart.entity.Cart;
import com.vantruong.cart.entity.CartItem;
import com.vantruong.cart.entity.SizeQuantity;
import com.vantruong.cart.exception.ErrorCode;
import com.vantruong.cart.exception.InsufficientProductQuantityException;
import com.vantruong.cart.exception.NotFoundException;
import com.vantruong.cart.repository.CartRepository;
import com.vantruong.cart.repository.client.InventoryClient;
import com.vantruong.cart.repository.client.ProductClient;
import com.vantruong.cart.service.CartService;
import com.vantruong.common.dto.SizeQuantityDto;
import com.vantruong.common.dto.request.DeleteCartItemsRequest;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.dto.response.ProductResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {

  CartRepository cartRepository;
  ProductClient productClient;
  InventoryClient inventoryClient;

  public Cart findById(String email) {
    return cartRepository.findById(email).orElseThrow(()
            -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.CART_NOT_FOUND));
  }

  private Cart findByIdOrCreate(String email) {
    return cartRepository.findById(email).orElseGet(() -> {
      Cart cart = new Cart(email, new ArrayList<>());
      return cartRepository.save(cart);
    });
  }

  private List<ProductResponse> getProductsByIds(List<CartItem> items) {
    List<Integer> itemIds = items.stream().map(CartItem::getProductId).toList();
    return productClient.getProductByIds(itemIds).getData();
  }

  private Map<Integer, ProductResponse> getProductMapById(List<ProductResponse> products) {
    return products.stream().collect(Collectors.toMap(ProductResponse::getId, p -> p));
  }

  @Override
  public CartResponse getCartById(String email) {
    Cart cart = findByIdOrCreate(email);
    List<CartItemResponse> cartItemResponses = new ArrayList<>();
    if (Objects.nonNull(cart.getItems())) {
      List<ProductResponse> products = getProductsByIds(cart.getItems());

      Map<Integer, ProductResponse> productMap = getProductMapById(products);

      cartItemResponses = cart.getItems().stream().map(item -> {
        ProductResponse product = productMap.get(item.getProductId());
        List<SizeQuantity> sizeQuantities = item.getSizeQuantities();
        List<SizeQuantityDto> sizeQuantityDtos = sizeQuantities.stream()
                .map(sizeQuantity -> SizeQuantityDto.builder()
                        .size(sizeQuantity.getSize())
                        .quantity(sizeQuantity.getQuantity())
                        .build()
                )
                .toList();
        return CartItemResponse.builder()
                .product(product)
                .sizeQuantities(sizeQuantityDtos)
                .build();
      }).toList();
    }

    return CartResponse.builder()
            .items(cartItemResponses)
            .build();
  }

  private CartItem buildCartItem(Integer productId, String size, Integer quantity) {
    List<SizeQuantity> sizeQuantities = new ArrayList<>();
    SizeQuantity sizeQuantity = SizeQuantity.builder()
            .size(size)
            .quantity(quantity)
            .build();
    sizeQuantities.add(sizeQuantity);
    return CartItem.builder()
            .productId(productId)
            .sizeQuantities(sizeQuantities)
            .build();
  }

  private void addNewCartItemToCart(Cart cart, AddToCartRequest request) {
    List<CartItem> cartItems = cart.getItems();
    if (cartItems == null) {
      cartItems = new ArrayList<>();
    }
    CartItem cartItem = buildCartItem(request.getProductId(), request.getSize(), request.getQuantity());
    cartItems.add(cartItem);
    cart.setItems(cartItems);
    cartRepository.save(cart);
  }


  /*
   * giỏ hàng chưa có sản phẩm cần thêm
   * đã có sản phẩm mà size khác
   * đã có size đó => update quantity
   * */
  @Transactional
  public long addToCart(AddToCartRequest request) {
    Cart cart = findByIdOrCreate(request.getEmail());
    List<CartItem> cartItems = cart.getItems();

    if (cartItems == null) {
      addNewCartItemToCart(cart, request);
      return count(request.getEmail());
    }
    CartItem cartItem = cartItems.stream()
            .filter(ct -> ct.getProductId().equals(request.getProductId()))
            .findFirst()
            .orElse(null);
    if (cartItem == null) {
      addNewCartItemToCart(cart, request);
      return count(request.getEmail());
    }
    List<SizeQuantity> sizeQuantities = cartItem.getSizeQuantities();
    SizeQuantity existedSizeQuantity = findSizeQuantity(cartItem, request.getSize());
    if (existedSizeQuantity != null) {
      updateSizeQuantity(
              request.getEmail(),
              request.getProductId(),
              request.getSize(),
              request.getQuantity()
      );
      return count(request.getEmail());

    } else {
      SizeQuantity sizeQuantity = SizeQuantity.builder()
              .size(request.getSize())
              .quantity(request.getQuantity())
              .build();
      sizeQuantities.add(sizeQuantity);
      cartRepository.save(cart);
      return count(request.getEmail());
    }
  }

  private CartItem findCartItemByProductId(Cart cart, Integer productId) {
    if (cart.getItems() == null) return null;
    return cart.getItems().stream()
            .filter(cartItem -> cartItem.getProductId().equals(productId))
            .findFirst()
            .orElse(null);
  }

  private SizeQuantity findSizeQuantity(CartItem cartItem, String size) {
    if (cartItem.getSizeQuantities() == null) return null;
    return cartItem.getSizeQuantities().stream()
            .filter(item -> item.getSize().equals(size))
            .findFirst()
            .orElse(null);
  }

  @Override
  @Transactional
  public long removeFromCart(DeleteCartRequest request) {
    Cart cart = findById(request.getEmail());

    CartItem cartItem = findCartItemByProductId(cart, request.getProductId());
    if (cartItem != null) {
      List<SizeQuantity> sizeQuantities = cartItem.getSizeQuantities();
      sizeQuantities.removeIf(sizeQuantity -> sizeQuantity.getSize().equals(request.getSize()));

      cartRepository.save(cart);
    }
    return count(request.getEmail());
  }

  @Override
  public long updateQuantity(UpdateCartRequest request) {
    changeCartItemQuantity(request.getEmail(), request.getProductId(), request.getSize(), request.getQuantity());
    return count(request.getEmail());

  }

  private Boolean updateSizeQuantity(String email, Integer productId, String size, Integer quantity) {
    Cart cart = findById(email);

    CartItem cartItem = findCartItemByProductId(cart, productId);
    if (cartItem == null) {
      return false;
    }

    SizeQuantity sizeQuantity = findSizeQuantity(cartItem, size);
    if (sizeQuantity == null) {
      return false;
    }

    int newQuantity = sizeQuantity.getQuantity() + quantity;
    Integer stock = getQuantityInStock(productId, size);
    if (stock == null || newQuantity > stock) {
      throw new InsufficientProductQuantityException(
              ErrorCode.UNPROCESSABLE_ENTITY,
              MessageConstant.INSUFFICIENT_PRODUCT_QUANTITY
      );
    }

    sizeQuantity.setQuantity(newQuantity);
    cartRepository.save(cart);
    return true;

  }

  private Boolean changeCartItemQuantity(String email, Integer productId, String size, Integer quantity) {
    Cart cart = findById(email);

    CartItem cartItem = findCartItemByProductId(cart, productId);
    if (cartItem == null) {
      return false;
    }

    SizeQuantity sizeQuantity = findSizeQuantity(cartItem, size);
    if (sizeQuantity == null) {
      return false;
    }

    Integer stock = getQuantityInStock(productId, size);
    if (stock == null || quantity > stock) {
      throw new InsufficientProductQuantityException(
              ErrorCode.UNPROCESSABLE_ENTITY,
              MessageConstant.INSUFFICIENT_PRODUCT_QUANTITY
      );
    }

    sizeQuantity.setQuantity(quantity);
    cartRepository.save(cart);
    return true;

  }

  private Integer getQuantityInStock(Integer productId, String size) {
    ProductQuantityRequest checkProductQuantityRequest = ProductQuantityRequest.builder()
            .productId(productId)
            .size(size)
            .build();
    return inventoryClient.checkProductQuantityById(checkProductQuantityRequest).getData();
  }

  @Override
  public void removeItemsFromCart(DeleteCartItemsRequest request) {
    Cart cart = findById(request.getEmail());
    for (com.vantruong.common.dto.CartItem item : request.getItems()) {
      CartItem cartItem = findCartItemByProductId(cart, item.getProductId());
      if (cartItem != null) {
        List<SizeQuantity> sizeQuantities = cartItem.getSizeQuantities();
        if (sizeQuantities != null) {
          cartItem.getSizeQuantities().removeIf(it -> it.getSize().equals(item.getSize()));
        }
      }
    }
    cartRepository.save(cart);
  }

  @Override
  public List<SizeQuantityDto> getByEmailAndProductId(String email, int productId) {
    Cart cart = findById(email);
    CartItem cartItem = findCartItemByProductId(cart, productId);

    if (cartItem != null) {
      List<SizeQuantity> sizeQuantities = cartItem.getSizeQuantities();
      if (sizeQuantities == null) return null;
      return sizeQuantities.stream()
              .map(sizeQuantity ->
                      SizeQuantityDto.builder()
                              .size(sizeQuantity.getSize())
                              .quantity(sizeQuantity.getQuantity())
                              .build()
              ).toList();
    }
    return null;
  }

  @Override
  public long count(String email) {
    Cart cart = findById(email);
    List<CartItem> cartItems = cart.getItems();

    if (cartItems.isEmpty()) return 0;

    return cartItems.stream()
            .mapToLong(item -> (long) item.getSizeQuantities().size()
            ).sum();
  }
}
