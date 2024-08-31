package com.vantruong.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailRequest {
  private int productId;
  private int quantity;
  private String size;
}
