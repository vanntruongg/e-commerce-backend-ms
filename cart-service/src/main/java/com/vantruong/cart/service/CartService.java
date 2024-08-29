package com.vantruong.cart.service;

import com.vantruong.cart.dto.request.AddToCartRequest;
import com.vantruong.cart.dto.request.DeleteCartRequest;
import com.vantruong.cart.dto.response.CartResponse;
import com.vantruong.cart.dto.request.UpdateCartRequest;
import com.vantruong.common.dto.SizeQuantityDto;
import com.vantruong.common.dto.request.DeleteCartItemsRequest;

import java.util.List;


public interface CartService {
  CartResponse getCartById(String email);

  Boolean addToCart(AddToCartRequest cartItemDto);

  Boolean removeFromCart(DeleteCartRequest request);

  Boolean updateQuantity(UpdateCartRequest request);

  void removeItemsFromCart(DeleteCartItemsRequest removeItemsCartRequest);

  List<SizeQuantityDto> getByEmailAndProductId(String email, int productId);
}
