package com.vantruong.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderRevenueDto {
  private long totalOrder;
  private double totalRevenue;
  private int periodValue;
}
