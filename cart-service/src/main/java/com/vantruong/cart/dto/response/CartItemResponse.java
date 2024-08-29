package com.vantruong.cart.dto.response;

import com.vantruong.common.dto.SizeQuantityDto;
import com.vantruong.common.dto.response.ProductResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CartItemResponse {
  private ProductResponse product;
  private List<SizeQuantityDto> sizeQuantities;
}
