package paymentservice.provider;

import paymentservice.dto.PaymentRequest;
import paymentservice.enums.PaymentMethod;

public interface PaymentProvider<T> {
  T getProvider(PaymentRequest method);
}
