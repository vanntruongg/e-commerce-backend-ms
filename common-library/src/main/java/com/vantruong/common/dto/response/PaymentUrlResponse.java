package com.vantruong.common.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentUrlResponse {
  private String paymentUrl;
}
