package paymentservice.gateway.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import paymentservice.dto.PaymentRequest;
import paymentservice.dto.PaymentResponse;
import paymentservice.gateway.PaymentGateway;
import paymentservice.provider.PaymentProvider;
import paymentservice.service.PaymentProcessingService;

@Component
@RequiredArgsConstructor
public class PaymentGatewayImpl implements PaymentGateway {
  private final PaymentProvider<PaymentProcessingService> paymentProvider;
  @Override
  public PaymentResponse makePayment(PaymentRequest paymentRequest) {
    return paymentProvider.getProvider(paymentRequest).processPayment(paymentRequest);
  }
}
