package paymentservice.service;

import paymentservice.dto.PaymentRequest;
import paymentservice.dto.PaymentResponse;


public interface PaymentProcessingService {
  PaymentResponse processPayment(PaymentRequest paymentRequest);
}
