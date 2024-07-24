package com.vantruong.payment.dto;

import com.vantruong.payment.enums.PaymentStatus;
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
  private Integer method;
  private Integer orderId;
}
