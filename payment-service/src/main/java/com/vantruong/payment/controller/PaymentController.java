package com.vantruong.payment.controller;

import com.vantruong.payment.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vantruong.payment.constant.ApiEndpoint;
import com.vantruong.payment.constant.MessageConstant;
import com.vantruong.payment.service.PaymentService;

@RestController
@RequestMapping(ApiEndpoint.PAYMENTS)
@RequiredArgsConstructor
public class PaymentController {
  private final PaymentService paymentService;

  @GetMapping(ApiEndpoint.GET_BY_ORDER_ID)
  public ResponseEntity<CommonResponse<Object>> getPaymentByOrderId(@PathVariable("id") int orderId) {

    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(paymentService.getPaymentByOrderId(orderId))
            .build());
  }

//  @GetMapping(ApiEndpoint.UPDATE_PAYMENT_STATUS)
//  public ResponseEntity<CommonResponse<Object>> updateOrderPaymentStatusToPaid(@PathVariable("id") int orderId) {
//
//    return ResponseEntity.ok().body(CommonResponse.builder()
//            .isSuccess(true)
//            .message(MessageConstant.SUCCESS)
//            .data(paymentService.updateOrderPaymentStatusToPaid(orderId))
//            .build());
//  }
}
