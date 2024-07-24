package com.vantruong.payment.service;

import com.vantruong.payment.dto.PaymentMethodDto;
import com.vantruong.payment.entity.PaymentMethod;

import java.util.List;

public interface PaymentMethodService {
  PaymentMethod findById(int paymentMethodId);

  List<PaymentMethodDto> findAll();
}
