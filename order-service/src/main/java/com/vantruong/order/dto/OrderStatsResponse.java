package com.vantruong.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatsResponse implements HasMonth {
        private String month;
        private Number totalOrders;

  @Override
  public String getMonth() {
    return month;
  }

  @Override
  public Number getValue() {
    return totalOrders;
  }
}
