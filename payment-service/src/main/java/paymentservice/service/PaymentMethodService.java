package paymentservice.service;

import paymentservice.entity.PaymentMethod;

public interface PaymentMethodService {
  PaymentMethod findById(int paymentMethodId);
}
