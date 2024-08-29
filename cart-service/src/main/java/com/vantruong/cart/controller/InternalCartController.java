package com.vantruong.cart.controller;

import com.vantruong.cart.common.CommonResponse;
import com.vantruong.cart.constant.ApiEndpoint;
import com.vantruong.cart.constant.MessageConstant;
import com.vantruong.cart.service.CartService;
import com.vantruong.common.dto.request.DeleteCartItemsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiEndpoint.INTERNAL + ApiEndpoint.CART)
@RequiredArgsConstructor
public class InternalCartController {
  private final CartService cartService;

//  @DeleteMapping(ApiEndpoint.CART_DELETE_ITEMS)
//  public ResponseEntity<CommonResponse<Object>> requestOrder(
//          @RequestBody DeleteCartItemsRequest removeItemsCartRequest
//  ) {
//    return ResponseEntity.ok().body(CommonResponse.builder()
//            .isSuccess(true)
//            .message(MessageConstant.UPDATE_SUCCESS)
//            .data(cartService.removeItemsFromCart(removeItemsCartRequest))
//            .build());
//  }
}
