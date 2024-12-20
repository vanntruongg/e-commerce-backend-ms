package com.vantruong.cart.dto.response;

import com.vantruong.cart.viewmodel.ProductVm;
import com.vantruong.cart.viewmodel.SizeQuantityVm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CartItemResponse {
  private ProductVm product;
  private List<SizeQuantityVm> sizeQuantities;
}
