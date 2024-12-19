package com.vantruong.cart.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
  private Long productId;
  private List<SizeQuantity> sizeQuantities;
}
