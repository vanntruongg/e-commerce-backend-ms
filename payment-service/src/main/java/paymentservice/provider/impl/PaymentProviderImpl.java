package paymentservice.provider.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import paymentservice.dto.PaymentRequest;
import paymentservice.entity.PaymentMethod;
import paymentservice.provider.PaymentProvider;
import paymentservice.service.PaymentMethodService;
import paymentservice.service.PaymentProcessingService;
import paymentservice.service.impl.CODPayServiceImpl;
import paymentservice.service.impl.MomoServiceImpl;
import paymentservice.service.impl.VNPayServiceImpl;
import paymentservice.service.impl.ZaloPayServiceImpl;

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
