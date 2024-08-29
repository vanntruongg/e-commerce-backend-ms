package com.vantruong.order.service.payment;

import java.util.Map;

public interface VNPayService {
  String generatePaymentUrl(Integer orderId, double amount);
   Boolean handleVNPayCallBack(Map<String, String> params);
}
