package com.vantruong.orderorchestrator.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {
  public static final String ORDER_ORCHESTRATOR = "/order-orchestrator";
  public static final String PAYMENT_CALLBACK = "/payment-callback";
  public static final String VN_PAY_PAYMENT_CALLBACK = "/vnpay-callback";
}
