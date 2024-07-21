package paymentservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paymentservice.constant.MessageConstant;
import paymentservice.entity.PaymentMethod;
import paymentservice.exception.ErrorCode;
import paymentservice.exception.NotFoundException;
import paymentservice.repository.PaymentMethodRepository;
import paymentservice.service.PaymentMethodService;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
  private final PaymentMethodRepository paymentMethodRepository;


  @Override
  public PaymentMethod findById(int paymentMethodId) {
    return paymentMethodRepository.findById(paymentMethodId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PAYMENT_METHOD_NOT_FOUND));
  }
}
