package com.vantruong.payment.provider.impl;

import com.vantruong.payment.dto.request.PaymentRequest;
import com.vantruong.payment.service.impl.MomoServiceImpl;
import com.vantruong.payment.service.impl.VNPayServiceImpl;
import com.vantruong.payment.service.impl.ZaloPayServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.vantruong.payment.entity.PaymentMethod;
import com.vantruong.payment.provider.PaymentProvider;
import com.vantruong.payment.service.PaymentMethodService;
import com.vantruong.payment.service.PaymentProcessingService;
import com.vantruong.payment.service.impl.CODPayServiceImpl;

@Service
@RequiredArgsConstructor
public class PaymentProviderImpl implements PaymentProvider<PaymentProcessingService> {

  private final ApplicationContext applicationContext;
  private final PaymentMethodService paymentMethodService;
  @Override
  public PaymentProcessingService getProvider(PaymentRequest request) {
    PaymentMethod paymentMethod = paymentMethodService.findById(request.getMethodId());
    return switch (paymentMethod.getMethod()) {
      case COD -> applicationContext.getBean(CODPayServiceImpl.class);
      case VN_PAY -> applicationContext.getBean(VNPayServiceImpl.class);
      case ZALO_PAY -> applicationContext.getBean(ZaloPayServiceImpl.class);
      case MOMO -> applicationContext.getBean(MomoServiceImpl.class);
      default -> throw new UnsupportedOperationException("Notification method: " + paymentMethod.getMethod() + " not supported");
    };
  }
}
