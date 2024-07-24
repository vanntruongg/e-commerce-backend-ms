package com.vantruong.cart.service;

import com.vantruong.cart.dto.CartItemDto;
import com.vantruong.cart.dto.CartResponse;
import com.vantruong.cart.dto.UpdateQuantityRequest;
import com.vantruong.cart.dto.internal.RemoveItemsCartRequest;


public interface CartService {
  CartResponse getCartById(String email);

  Boolean addToCart(CartItemDto cartItemDto);

  Boolean removeFromCart(String emailUser, int productId);

  Boolean updateQuantity(UpdateQuantityRequest request);

  Boolean removeItemsFromCart(RemoveItemsCartRequest removeItemsCartRequest);
}
