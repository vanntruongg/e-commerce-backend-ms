package com.vantruong.cart.service.impl;

import com.vantruong.cart.dto.*;
import com.vantruong.cart.dto.internal.RemoveItemsCartRequest;
import com.vantruong.cart.entity.Product;
import com.vantruong.cart.repository.CartRepository;
import com.vantruong.cart.repository.client.ProductClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vantruong.cart.constant.MessageConstant;
import vantruong.cartservice.dto.*;
import com.vantruong.cart.entity.Cart;
import com.vantruong.cart.entity.Item;
import com.vantruong.cart.exception.ErrorCode;
import com.vantruong.cart.exception.InsufficientProductQuantityException;
import com.vantruong.cart.exception.NotFoundException;
import com.vantruong.cart.service.CartService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {

  CartRepository cartRepository;
  ProductClient productClient;

  private Cart findById(String email) {
    return cartRepository.findById(email).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.CART_NOT_FOUND));
  }

  private Cart findByIdOrCreate(String email) {
    return cartRepository.findById(email).orElseGet(() -> {
      Cart cart = new Cart(email, new ArrayList<>(), 0.0);
      return cartRepository.save(cart);
    });
  }

  private List<Product> getProductsByIds(List<Item> items) {
      List<Integer> itemIds = items.stream().map(Item::getProductId).toList();
      return productClient.getProductByIds(itemIds).getData();
  }

  private Map<Integer, Product> getProductMapById(List<Product> products) {
    return products.stream().collect(Collectors.toMap(Product::getId, p -> p));
  }

  @Override
  public CartResponse getCartById(String email) {
    Cart cart = findByIdOrCreate(email);
    List<ItemResponse> itemResponse = new ArrayList<>();
    if(Objects.nonNull(cart.getItems())) {
      List<Product> products = getProductsByIds(cart.getItems());

      Map<Integer, Product> productMap = getProductMapById(products);

      itemResponse = cart.getItems().stream().map(item -> {
        Product product = productMap.get(item.getProductId());
        return ItemResponse.builder().product(product).quantity(item.getQuantity()).build();
      }).toList();
    }

    return CartResponse.builder()
            .email(cart.getEmail())
            .items(itemResponse)
            .totalPrice(cart.getTotalPrice())
            .build();
  }

  @Override
  @Transactional
  public Boolean addToCart(CartItemDto cartItemDto) {
    // find the cart by email
    Cart cartItem = findByIdOrCreate(cartItemDto.getEmail());

    // check and initialize the items list if it doesn't exist
    List<Item> items = cartItem.getItems();
    if (items == null) {
      items = new ArrayList<>();
      cartItem.setItems(items);
    }

    Item existedItem = isItemAlreadyInCart(items, cartItemDto.getItemDto().getProductId());
    if (existedItem != null) {
      Integer stock = productClient.getStockProductById(existedItem.getProductId()).getData();
      // if item already, update quantity
      int newQuantity = existedItem.getQuantity() + cartItemDto.getItemDto().getQuantity();
      if(newQuantity > stock) {
        throw new InsufficientProductQuantityException(ErrorCode.UNPROCESSABLE_ENTITY, MessageConstant.INSUFFICIENT_PRODUCT_QUANTITY);
      }
      existedItem.setQuantity(newQuantity);
    } else {
      // if item doesn't exist, add it to cart
      Item item = toItem(cartItemDto.getItemDto());
      items.add(item);
    }
    // calculate and update the total price of the cart
    cartItem.setTotalPrice(calculatorTotalPrice(cartItem.getItems()));
    cartRepository.save(cartItem);
    return true;
  }

  // check if an item already exists in the cart
  private Item isItemAlreadyInCart(List<Item> items, int idNewItem) {
    return items.stream()
            .filter(item -> item.getProductId() == idNewItem)
            .findFirst()
            .orElse(null);
  }

  // calculate the total price of the cart
  private double calculatorTotalPrice(List<Item> items) {
    List<Product> products = getProductsByIds(items);
    Map<Integer, Product> productMap = getProductMapById(products);

    return items.stream()
            .mapToDouble(item -> {
              Product product = productMap.get(item.getProductId());
              if (Objects.nonNull(product)) {
                return item.getQuantity() * product.getPrice();
              }
              return 0.0;
            })
            .sum();
  }

  // map dto to entity
  private Item toItem(ItemDto itemDto) {
    return Item.builder()
            .productId(itemDto.getProductId())
            .quantity(itemDto.getQuantity())
            .build();
  }

  @Override
  @Transactional
  public Boolean removeFromCart(String emailUser, int itemId) {
    Cart cartItem = findById(emailUser);

    if (cartItem != null) {
      List<Item> items = cartItem.getItems();
      items.removeIf(item -> item.getProductId() == itemId);

      double totalPrice = calculatorTotalPrice(cartItem.getItems());
      cartItem.setTotalPrice(totalPrice);
      cartRepository.save(cartItem);
      return true;
    }
    return false;
  }

  @Override
  public Boolean updateQuantity(UpdateQuantityRequest request) {
    Cart cartItem = findById(request.getEmail());

    Optional<Item> foundItem = cartItem.getItems().stream()
            .filter(item -> item.getProductId() == request.getItemId()).findFirst();

    if (foundItem.isPresent()) {
      Integer stock = productClient.getStockProductById(foundItem.get().getProductId()).getData();
      if (request.getQuantity() > stock) {
        throw new InsufficientProductQuantityException(ErrorCode.UNPROCESSABLE_ENTITY, MessageConstant.INSUFFICIENT_PRODUCT_QUANTITY);
      } else {
        foundItem.get().setQuantity(request.getQuantity());
        cartItem.setTotalPrice(calculatorTotalPrice(cartItem.getItems()));
        cartRepository.save(cartItem);
      }
      return true;
    }
    return false;
  }

  @Override
  @Transactional
  public Boolean removeItemsFromCart(RemoveItemsCartRequest removeItemsCartRequest) {
    Cart cartItem = findById(removeItemsCartRequest.getEmail());
    Boolean itemsRemoved = cartItem.getItems().removeIf(item -> removeItemsCartRequest.getProductIds().contains(item.getProductId()));

    cartRepository.save(cartItem);
    return itemsRemoved;
  }
}
