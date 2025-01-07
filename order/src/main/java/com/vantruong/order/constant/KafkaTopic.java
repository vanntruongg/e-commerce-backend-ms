package com.vantruong.order.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class KafkaTopic {
  public static final String ORDER_CREATED = "order-created";
  public static final String PAYMENT_SUCCESS = "payment-success";
  public static final String PAYMENT = "payment";
}
