package com.vantruong.cart.service;

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
import com.vantruong.cart.util.AuthenticationUtils;
import com.vantruong.cart.viewmodel.*;
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
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CartService {
  CartRepository cartRepository;
  ProductService productService;
  InventoryService inventoryService;

  public Cart findById() {
    String email = AuthenticationUtils.extractUserId();
    return cartRepository.findById(email).orElseThrow(()
            -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.CART_NOT_FOUND));
  }

  private Cart findByIdOrCreate() {
    String email = AuthenticationUtils.extractUserId();
    return cartRepository.findById(email).orElseGet(() -> {
      Cart cart = new Cart(email, new ArrayList<>());
      return cartRepository.save(cart);
    });
  }

  private List<ProductVm> getProductsByIds(List<CartItem> items) {
    List<Long> itemIds = items.stream().map(CartItem::getProductId).toList();
    return productService.getProductByIds(itemIds);
  }

  private Map<Long, ProductVm> getProductMapById(List<ProductVm> products) {
    return products.stream().collect(Collectors.toMap(ProductVm::id, p -> p));
  }

  public CartResponse getCartById() {
    Cart cart = findByIdOrCreate();
    List<CartItemResponse> cartItemResponses = new ArrayList<>();
    if (Objects.nonNull(cart.getItems())) {
      List<ProductVm> products = getProductsByIds(cart.getItems());

      Map<Long, ProductVm> productMap = getProductMapById(products);

      cartItemResponses = cart.getItems().stream().map(item -> {
        ProductVm product = productMap.get(item.getProductId());
        List<SizeQuantity> sizeQuantities = item.getSizeQuantities();
        List<SizeQuantityVm> sizeQuantityDtos = new ArrayList<>();
        if (sizeQuantities != null) {
          sizeQuantityDtos = sizeQuantities.stream()
                  .map(sizeQuantity -> new SizeQuantityVm(sizeQuantity.getSize(), sizeQuantity.getQuantity()))
                  .toList();
        }
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

  private CartItem buildCartItem(Long productId, String size, Integer quantity) {
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
    Cart cart = findByIdOrCreate();
    List<CartItem> cartItems = cart.getItems();

    if (cartItems == null) {
      addNewCartItemToCart(cart, request);
      return count();
    }
    CartItem cartItem = cartItems.stream()
            .filter(ct -> ct.getProductId().equals(request.getProductId()))
            .findFirst()
            .orElse(null);
    if (cartItem == null) {
      addNewCartItemToCart(cart, request);
      return count();
    }
    List<SizeQuantity> sizeQuantities = cartItem.getSizeQuantities();
    if (sizeQuantities == null) {
      sizeQuantities = new ArrayList<>();
    }
    SizeQuantity existedSizeQuantity = findSizeQuantity(cartItem, request.getSize());
    if (existedSizeQuantity != null) {
      updateSizeQuantity(
              request.getProductId(),
              request.getSize(),
              request.getQuantity()
      );

    } else {
      SizeQuantity sizeQuantity = SizeQuantity.builder()
              .size(request.getSize())
              .quantity(request.getQuantity())
              .build();
      sizeQuantities.add(sizeQuantity);
      cartItem.setSizeQuantities(sizeQuantities);
      cartRepository.save(cart);
    }
    return count();
  }

  private CartItem findCartItemByProductId(Cart cart, Long productId) {
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

  @Transactional
  public long removeFromCart(DeleteCartRequest request) {
    Cart cart = findById();

    CartItem cartItem = findCartItemByProductId(cart, request.getProductId());
    if (cartItem != null) {
      List<SizeQuantity> sizeQuantities = cartItem.getSizeQuantities();
      sizeQuantities.removeIf(sizeQuantity -> sizeQuantity.getSize().equals(request.getSize()));

      if (sizeQuantities.isEmpty()) {
        cart.getItems().remove(cartItem);
      }
      cartRepository.save(cart);
    }
    return count();
  }

  public long updateQuantity(UpdateCartRequest request) {
    changeCartItemQuantity(request.getProductId(), request.getSize(), request.getQuantity());
    return count();

  }

  private void updateSizeQuantity(Long productId, String size, Integer quantity) {
    Cart cart = findById();

    CartItem cartItem = findCartItemByProductId(cart, productId);
    if (cartItem == null) {
      return;
    }

    SizeQuantity sizeQuantity = findSizeQuantity(cartItem, size);
    if (sizeQuantity == null) {
      return;
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

  }

  private void changeCartItemQuantity(Long productId, String size, Integer quantity) {
    Cart cart = findById();

    CartItem cartItem = findCartItemByProductId(cart, productId);
    if (cartItem == null) {
      return;
    }

    SizeQuantity sizeQuantity = findSizeQuantity(cartItem, size);
    if (sizeQuantity == null) {
      return;
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

  }

  private Integer getQuantityInStock(Long productId, String size) {
    ProductQuantityCheckVm checkProductQuantityRequest = new ProductQuantityCheckVm(productId, size);
    return inventoryService.checkProductQuantityById(checkProductQuantityRequest);
  }

  public void removeItemsFromCart(CartItemDeleteVm cartItemDeleteVm) {
    Cart cart = cartRepository.findById(cartItemDeleteVm.email()).orElseThrow(()
            -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.CART_NOT_FOUND));
    for (CartItemVm item : cartItemDeleteVm.cartItemVms()) {
      CartItem cartItem = findCartItemByProductId(cart, item.productId());
      if (cartItem != null) {
        List<SizeQuantity> sizeQuantities = cartItem.getSizeQuantities();
        if (sizeQuantities != null) {
          sizeQuantities.removeIf(it -> it.getSize().equals(item.size()));
          if (sizeQuantities.isEmpty()) {
            cart.getItems().remove(cartItem);
          }
        }
      }
    }
    cartRepository.save(cart);
  }

  public List<SizeQuantityVm> getByEmailAndProductId(Long productId) {
    Cart cart = findById();
    CartItem cartItem = findCartItemByProductId(cart, productId);

    if (cartItem != null) {
      List<SizeQuantity> sizeQuantities = cartItem.getSizeQuantities();
      if (sizeQuantities == null) return null;
      return sizeQuantities.stream()
              .map(sizeQuantity -> new SizeQuantityVm(sizeQuantity.getSize(), sizeQuantity.getQuantity()))
              .toList();
    }
    return null;
  }

  public long count() {
    Cart cart = findById();
    List<CartItem> cartItems = cart.getItems();

    if (cartItems == null || cartItems.isEmpty()) return 0;
    return cartItems.stream()
            .mapToLong(item -> {
                      if (item.getSizeQuantities() == null) return 0;
                      return item.getSizeQuantities().size();
                    }
            ).sum();
  }
}
