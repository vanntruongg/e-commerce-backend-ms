package com.vantruong.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vantruong.payment.common.CommonResponse;
import com.vantruong.payment.constant.ApiEndpoint;
import com.vantruong.payment.constant.MessageConstant;
import com.vantruong.payment.service.PaymentMethodService;

@RestController
@RequestMapping(ApiEndpoint.PAYMENTS)
@RequiredArgsConstructor
public class PaymentMethodController {
  private final PaymentMethodService paymentMethodService;

  @GetMapping(ApiEndpoint.METHODS)
  public ResponseEntity<CommonResponse<Object>> getAllPaymentMethod() {

    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(paymentMethodService.findAll())
            .build());
  }

}
