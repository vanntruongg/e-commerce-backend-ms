package com.vantruong.cart.entity;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
  private Integer productId;
  private List<SizeQuantity> sizeQuantities;
}
