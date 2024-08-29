package com.vantruong.order.service.payment;

import com.vantruong.order.dto.PaymentMethodDto;
import com.vantruong.order.entity.PaymentMethod;

import java.util.List;

public interface PaymentMethodService {
  PaymentMethod findById(int paymentMethodId);

  List<PaymentMethodDto> findAll();
}
