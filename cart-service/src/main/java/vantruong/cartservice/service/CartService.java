package vantruong.cartservice.service;

import vantruong.cartservice.dto.CartItemDto;
import vantruong.cartservice.dto.CartResponse;
import vantruong.cartservice.dto.UpdateQuantityRequest;
import vantruong.cartservice.dto.internal.RemoveItemsCartRequest;


public interface CartService {
  CartResponse getCartById(String email);

  Boolean addToCart(CartItemDto cartItemDto);

  Boolean removeFromCart(String emailUser, int productId);

  Boolean updateQuantity(UpdateQuantityRequest request);

  Boolean removeItemsFromCart(RemoveItemsCartRequest removeItemsCartRequest);
}
