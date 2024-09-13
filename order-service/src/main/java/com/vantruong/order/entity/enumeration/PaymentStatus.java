package com.vantruong.order.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
  PENDING,
  COMPLETED,
  CANCELED
}
