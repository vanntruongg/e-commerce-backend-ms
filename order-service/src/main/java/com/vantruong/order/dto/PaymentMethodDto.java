package com.vantruong.order.dto;

import com.vantruong.order.entity.enumeration.PaymentMethod;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDto {
  private int id;
  private String method;
  private PaymentMethod slug;
  private String description;
}
