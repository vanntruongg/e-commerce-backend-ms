package com.vantruong.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartItem {
  private Integer productId;
  private String size;
}
