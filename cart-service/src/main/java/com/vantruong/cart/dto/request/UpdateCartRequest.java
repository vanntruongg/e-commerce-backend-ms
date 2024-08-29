package com.vantruong.cart.dto.request;

import lombok.Getter;

@Getter
public class UpdateCartRequest {
  private String email;
  private int productId;
  private String size;
  private int quantity;
}
