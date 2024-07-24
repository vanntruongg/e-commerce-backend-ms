package com.vantruong.payment.provider;

import com.vantruong.payment.dto.request.PaymentRequest;

public interface PaymentProvider<T> {
  T getProvider(PaymentRequest method);
}
