package vantruong.cartservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vantruong.cartservice.common.CommonResponse;
import vantruong.cartservice.constant.MessageConstant;
import vantruong.cartservice.dto.internal.RemoveItemsCartRequest;
import vantruong.cartservice.service.CartService;

import static vantruong.cartservice.constant.ApiEndpoint.*;

@RestController
@RequestMapping(INTERNAL + CART)
@RequiredArgsConstructor
public class InternalCartController {
  private final CartService cartService;

  @DeleteMapping(CART_DELETE_ITEMS)
  public ResponseEntity<CommonResponse<Object>> requestOrder(
          @RequestBody RemoveItemsCartRequest removeItemsCartRequest
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_SUCCESS)
            .data(cartService.removeItemsFromCart(removeItemsCartRequest))
            .build());
  }
}
