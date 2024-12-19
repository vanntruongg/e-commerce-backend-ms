package com.vantruong.cart.controller;

import com.vantruong.cart.common.CommonResponse;
import com.vantruong.cart.dto.request.AddToCartRequest;
import com.vantruong.cart.dto.request.DeleteCartRequest;
import com.vantruong.cart.dto.request.UpdateCartRequest;
import com.vantruong.cart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vantruong.cart.constant.MessageConstant;

import static com.vantruong.cart.constant.ApiEndpoint.*;

@RestController
@RequestMapping(CART)
public class CartController {
  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping(CART_GET_ALL)
  public ResponseEntity<CommonResponse<Object>> getAll() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(cartService.getCartById())
            .build());
  }

  @GetMapping(CART_GET_BY_PRODUCT_ID)
  public ResponseEntity<CommonResponse<Object>> getByEmailAndProductId(@RequestParam("productId") Long productId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(cartService.getByEmailAndProductId(productId))
            .build());
  }

  @GetMapping(CART_COUNT)
  public ResponseEntity<CommonResponse<Object>> updateQuantity() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(cartService.count())
            .build());
  }

  @PostMapping(ADD_TO_CART)
  public ResponseEntity<CommonResponse<Object>> addToCart(@RequestBody @Valid AddToCartRequest cartItemDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.ADD_TO_CART_SUCCESS)
            .data(cartService.addToCart(cartItemDto))
            .build());
  }

  @PostMapping(CART_DELETE)
  public ResponseEntity<CommonResponse<Object>> removeFromCart(@RequestBody DeleteCartRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_SUCCESS)
            .data(cartService.removeFromCart(request))
            .build());
  }

  @PostMapping(CART_UPDATE)
  public ResponseEntity<CommonResponse<Object>> updateQuantity(@RequestBody UpdateCartRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_SUCCESS)
            .data(cartService.updateQuantity(request))
            .build());
  }
}
