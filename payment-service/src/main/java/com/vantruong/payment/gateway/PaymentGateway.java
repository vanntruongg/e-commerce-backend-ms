package com.vantruong.payment.gateway;

import com.vantruong.payment.dto.request.PaymentRequest;
import com.vantruong.payment.dto.response.PaymentResponse;

public interface PaymentGateway {
  PaymentResponse makePayment(PaymentRequest paymentRequest);
}
