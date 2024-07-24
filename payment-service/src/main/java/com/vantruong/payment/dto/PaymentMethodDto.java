package com.vantruong.payment.dto;

import com.vantruong.payment.enums.PaymentMethod;
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
