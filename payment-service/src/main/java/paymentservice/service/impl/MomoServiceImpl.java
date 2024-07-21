package paymentservice.service.impl;

import org.springframework.stereotype.Service;
import paymentservice.dto.PaymentRequest;
import paymentservice.dto.PaymentResponse;
import paymentservice.service.PaymentProcessingService;

@Service
public class MomoServiceImpl implements PaymentProcessingService {
  @Override
  public PaymentResponse processPayment(PaymentRequest paymentRequest) {

    return PaymentResponse.builder()
            .urlPayment("Link thanh toán Momo")
            .build();
  }
}
