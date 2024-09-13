package com.vantruong.cart.controller;

import com.vantruong.cart.constant.ApiEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiEndpoint.INTERNAL + ApiEndpoint.CART)
public class InternalCartController {

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
