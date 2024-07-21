package paymentservice.gateway;

import paymentservice.dto.PaymentRequest;
import paymentservice.dto.PaymentResponse;

public interface PaymentGateway {
  PaymentResponse makePayment(PaymentRequest paymentRequest);
}
