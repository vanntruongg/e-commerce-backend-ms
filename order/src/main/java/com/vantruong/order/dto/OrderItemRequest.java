package com.vantruong.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderItemRequest {
  private Long productId;
  private int quantity;
  private String productName;
  private Double productPrice;
  private String productImage;
  private String size;
}
