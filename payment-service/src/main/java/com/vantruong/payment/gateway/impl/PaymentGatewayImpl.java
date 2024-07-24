package com.vantruong.payment.gateway.impl;

import com.vantruong.payment.dto.request.PaymentRequest;
import com.vantruong.payment.dto.response.PaymentResponse;
import com.vantruong.payment.provider.PaymentProvider;
import com.vantruong.payment.service.PaymentProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.vantruong.payment.gateway.PaymentGateway;

@Component
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway {
  private final PaymentProvider<PaymentProcessingService> paymentProvider;
  @Override
  public PaymentResponse makePayment(PaymentRequest paymentRequest) {
    return paymentProvider.getProvider(paymentRequest).processPayment(paymentRequest);
  }
}
