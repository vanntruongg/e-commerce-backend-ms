package com.vantruong.payment.service.impl;

import com.vantruong.payment.dto.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.vantruong.payment.dto.request.PaymentRequest;
import com.vantruong.payment.service.PaymentProcessingService;
import com.vantruong.payment.service.PaymentService;

@Service
@RequiredArgsConstructor
public class ZaloPayServiceImpl implements PaymentProcessingService {
  private final PaymentService paymentService;
  @Override
  public PaymentResponse processPayment(PaymentRequest paymentRequest) {
//    PaymentDto paymentDto = PaymentDto.builder()
//            .orderId(paymentRequest.getOrderId())
//            .amount(paymentRequest.getAmount())
//            .method(paymentRequest.getMethodId())
//            .status(PaymentStatus.PAID)
//            .build();
//    paymentService.createPayment(paymentDto);
    return PaymentResponse.builder()
            .urlPayment("Link thanh toán Zalo Pay")
            .build();
  }
}
