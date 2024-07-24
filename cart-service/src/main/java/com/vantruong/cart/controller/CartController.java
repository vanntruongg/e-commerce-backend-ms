package com.vantruong.cart.controller;

import com.vantruong.cart.common.CommonResponse;
import com.vantruong.cart.dto.CartItemDto;
import com.vantruong.cart.dto.UpdateQuantityRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vantruong.cart.constant.MessageConstant;
import com.vantruong.cart.service.CartService;

import static com.vantruong.cart.constant.ApiEndpoint.*;

@RestController
@RequestMapping(CART)
@RequiredArgsConstructor
public class CartController {
  private final CartService cartService;

  @GetMapping(CART_GET_ALL)
  public ResponseEntity<CommonResponse<Object>> getAll(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(cartService.getCartById(email))
            .build());
  }

  @PostMapping(ADD_TO_CART)
  public ResponseEntity<CommonResponse<Object>> addToCart(@RequestBody @Valid CartItemDto cartItemDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.ADD_TO_CART_SUCCESS)
            .data(cartService.addToCart(cartItemDto))
            .build());
  }

  @PostMapping(REMOVE_FROM_CART)
  public ResponseEntity<CommonResponse<Object>> removeFromCart(@RequestParam("email") String email, @RequestParam("id") int productId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_SUCCESS)
            .data(cartService.removeFromCart(email, productId))
            .build());
  }

  @PostMapping(CART_UPDATE)
  public ResponseEntity<CommonResponse<Object>> updateQuantity(@RequestBody UpdateQuantityRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_SUCCESS)
            .data(cartService.updateQuantity(request))
            .build());
  }
}
