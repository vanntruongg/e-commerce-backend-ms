package com.vantruong.payment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentRequest {
  private Integer orderId;
  private Integer methodId;
  private Double amount;
}
