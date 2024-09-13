package com.vantruong.cart.dto.request;

import lombok.Getter;

@Getter
public class DeleteCartRequest {
  private String email;
  private Long productId;
  private String size;
}
