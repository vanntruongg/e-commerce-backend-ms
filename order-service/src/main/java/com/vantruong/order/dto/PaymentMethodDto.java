package com.vantruong.order.dto;

import com.vantruong.order.entity.PaymentMethod;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDto {
  private int paymentMethodId;
  private String name;
  private PaymentMethod method;
  private String description;
}
