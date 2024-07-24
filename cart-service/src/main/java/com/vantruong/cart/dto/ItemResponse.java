package com.vantruong.cart.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.vantruong.cart.entity.Product;

@Getter
@Setter
@Builder
public class ItemResponse {
  private Product product;
  private int quantity;
}
