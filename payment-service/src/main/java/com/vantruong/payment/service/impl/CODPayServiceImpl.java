package com.vantruong.payment.service.impl;

import com.vantruong.payment.dto.PaymentDto;
import com.vantruong.payment.dto.request.PaymentRequest;
import com.vantruong.payment.dto.response.PaymentResponse;
import com.vantruong.payment.enums.PaymentStatus;
import com.vantruong.payment.service.PaymentProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.vantruong.payment.service.PaymentService;

@Service
@RequiredArgsConstructor
public class CODPayServiceImpl implements PaymentProcessingService {
  private final PaymentService paymentService;

  @Override
  public PaymentResponse processPayment(PaymentRequest paymentRequest) {
    PaymentDto paymentDto = PaymentDto.builder()
            .orderId(paymentRequest.getOrderId())
            .amount(paymentRequest.getAmount())
            .method(paymentRequest.getMethodId())
            .status(PaymentStatus.UNPAID)
            .build();
    paymentService.createPayment(paymentDto);
    return PaymentResponse.builder().build();
  }
}
