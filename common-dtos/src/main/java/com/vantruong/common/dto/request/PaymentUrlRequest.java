package com.vantruong.common.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentUrlRequest {
  private Integer methodId;
  private Integer orderId;
  private Double amount;
}
