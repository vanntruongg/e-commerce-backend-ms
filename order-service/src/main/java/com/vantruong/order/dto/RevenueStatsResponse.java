package com.vantruong.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RevenueStatsResponse implements HasMonth {
  private String month;
  private Number totalRevenue;

  @Override
  public String getMonth() {
    return month;
  }

  @Override
  public Number getValue() {
    return totalRevenue;
  }
}
