package com.vantruong.payment.service;

import com.vantruong.payment.dto.request.PaymentRequest;
import com.vantruong.payment.dto.response.PaymentResponse;


public interface PaymentProcessingService {
  PaymentResponse processPayment(PaymentRequest paymentRequest);
}
