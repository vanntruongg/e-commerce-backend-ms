package com.vantruong.order.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum OrderStatus {
  PENDING("PENDING"),
  ACCEPTED("ACCEPTED"),
  SHIPPING("SHIPPING"),
  COMPLETED("COMPLETED"),
  CANCELED("CANCELED");

  private final String orderStatus;

  public String getName() {
    return this.orderStatus;
  }

  public static OrderStatus findOrderStatus(String stt) {
    return Stream.of(OrderStatus.values())
            .filter(status -> status.name().equals(stt))
            .findFirst()
            .orElseThrow(IllegalAccessError::new);
  }
}
