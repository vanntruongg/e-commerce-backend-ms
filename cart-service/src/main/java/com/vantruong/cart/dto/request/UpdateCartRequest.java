package com.vantruong.cart.dto.request;

import lombok.Getter;

@Getter
public class UpdateCartRequest {
  private Long productId;
  private String size;
  private int quantity;
}
