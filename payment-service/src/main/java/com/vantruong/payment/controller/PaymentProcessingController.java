package com.vantruong.payment.controller;

import com.vantruong.payment.common.CommonResponse;
import com.vantruong.payment.constant.ApiEndpoint;
import com.vantruong.payment.constant.MessageConstant;
import com.vantruong.payment.dto.request.PaymentRequest;
import com.vantruong.payment.gateway.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiEndpoint.PAYMENTS)
@RequiredArgsConstructor
public class PaymentProcessingController {
  private final PaymentGateway paymentGateway;

  @GetMapping(ApiEndpoint.PAY)
  public ResponseEntity<CommonResponse<Object>> getOrderPayment(@RequestBody PaymentRequest paymentRequest) {

    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.PAYMENT_SUCCESS)
            .data(paymentGateway.makePayment(paymentRequest))
            .build());

  }
}
