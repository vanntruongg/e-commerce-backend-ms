package com.vantruong.common.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
  private int orderDetailId;

  private int productId;

  private int quantity;

  private String productName;

  private double productPrice;

  private String productImage;

  private String productSize;
}
