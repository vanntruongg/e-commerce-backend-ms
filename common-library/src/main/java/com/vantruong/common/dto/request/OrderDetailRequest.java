package com.vantruong.common.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailRequest {
  private int productId;
  private String productName;
  private int quantity;
  private double productPrice;
  private String productSize;
  private String productImage;
}
