package com.vantruong.payment.controller;

import com.vantruong.common.constant.InternalApiEndpoint;
import com.vantruong.common.dto.request.PaymentUrlRequest;
import com.vantruong.payment.common.CommonResponse;
import com.vantruong.payment.constant.MessageConstant;
import com.vantruong.payment.gateway.PaymentGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(InternalApiEndpoint.INTERNAL + InternalApiEndpoint.PAYMENTS)
@RequiredArgsConstructor
public class PaymentProcessingController {
  private final PaymentGateway paymentGateway;

  @PostMapping(InternalApiEndpoint.GET_PAYMENT_URL)
  public ResponseEntity<CommonResponse<Object>> getPaymentUrl(@RequestBody PaymentUrlRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.GET_LINK_PAYMENT_SUCCESS)
            .data(paymentGateway.getPaymentUrl(request))

            .build());
  }
}
