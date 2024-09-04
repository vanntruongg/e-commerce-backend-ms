package com.vantruong.order.dto;

import com.vantruong.order.entity.PaymentMethod;
import com.vantruong.order.enums.EPaymentMethod;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDto {
  private int id;
  private String method;
  private EPaymentMethod slug;
  private String description;
}
