package com.vantruong.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class OrderDetailDto {
  private int productId;
  private String productName;
  private int quantity;
  private double productPrice;
  private String size;
  private String productImage;
}
