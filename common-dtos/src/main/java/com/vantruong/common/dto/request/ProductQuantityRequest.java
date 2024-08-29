package com.vantruong.common.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductQuantityRequest {
  private Integer productId;
  private String size;
  private Integer quantity;
}
