package com.vantruong.cart.entity;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeQuantity {
  private String size;
  @Min(value = 0, message = "Quantity must be non-negative")
  private int quantity;
}
