package com.vantruong.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.vantruong.payment.dto.request.PaymentRequest;
import com.vantruong.payment.dto.response.PaymentResponse;
import com.vantruong.payment.service.PaymentProcessingService;
import com.vantruong.payment.service.PaymentService;

@Service
@RequiredArgsConstructor
public class MomoServiceImpl implements PaymentProcessingService {
  private final PaymentService paymentService;
  @Override
  public PaymentResponse processPayment(PaymentRequest paymentRequest) {

//      PaymentDto paymentDto = PaymentDto.builder()
//              .orderId(paymentRequest.getOrderId())
//              .amount(paymentRequest.getAmount())
//              .method(paymentRequest.getMethodId())
//              .status(PaymentStatus.UNPAID)
//              .build();
//      paymentService.createPayment(paymentDto);
    return PaymentResponse.builder()
            .urlPayment("Link thanh toán Momo")
            .build();
  }
}
