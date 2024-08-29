package com.vantruong.cart.dto.request;

import lombok.Getter;

@Getter
public class DeleteCartRequest {
  private String email;
  private int productId;
  private String size;
}
