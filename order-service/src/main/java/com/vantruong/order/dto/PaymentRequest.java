package com.vantruong.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentRequest {
  private Integer orderId;
  private int methodId;
  private double amount;
}
