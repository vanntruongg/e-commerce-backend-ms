package paymentservice.service;

import java.io.UnsupportedEncodingException;

public interface PaymentService {
  String createUrlPaymentOrder(long amount) throws UnsupportedEncodingException;
}
