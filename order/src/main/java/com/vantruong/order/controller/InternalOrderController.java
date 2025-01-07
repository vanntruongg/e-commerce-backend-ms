package com.vantruong.order.controller;

import com.vantruong.order.common.CommonResponse;
import com.vantruong.order.constant.MessageConstant;
import com.vantruong.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.vantruong.order.constant.ApiEndpoint.ORDER;
import static com.vantruong.order.constant.InternalApiEndpoint.CHECK_ORDER_BY_USER_PRODUCT_STATUS;
import static com.vantruong.order.constant.InternalApiEndpoint.INTERNAL;


@RestController
@RequestMapping(ORDER + INTERNAL)
@RequiredArgsConstructor
public class InternalOrderController {
  private final OrderService orderService;

  @GetMapping(CHECK_ORDER_BY_USER_PRODUCT_STATUS)
  public ResponseEntity<CommonResponse<Object>> checkOrderExistsByProductAndUserWithStatus(
          @PathVariable("email") String email,
          @PathVariable("id") Long productId
  ) {
     return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(orderService.isOrderCompleted(email, productId))
            .build());
  }
}
