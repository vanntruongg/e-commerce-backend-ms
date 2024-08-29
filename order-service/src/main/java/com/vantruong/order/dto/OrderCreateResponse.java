package com.vantruong.order.dto;

import com.vantruong.order.entity.PaymentMethod;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateResponse {
  private PaymentMethod paymentMethod;
  private String urlPayment;
}
