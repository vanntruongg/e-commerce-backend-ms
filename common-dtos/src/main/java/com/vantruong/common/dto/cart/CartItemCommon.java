package com.vantruong.common.dto.cart;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartItemCommon {
  private Long productId;
  private String size;
}
