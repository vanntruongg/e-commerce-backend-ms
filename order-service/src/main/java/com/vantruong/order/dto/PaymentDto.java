package com.vantruong.order.dto;

import com.vantruong.order.entity.enumeration.PaymentStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
  private Integer paymentId;
  private Double amount;
  private PaymentStatus status;
  private Integer methodId;
  private Integer orderId;
}
