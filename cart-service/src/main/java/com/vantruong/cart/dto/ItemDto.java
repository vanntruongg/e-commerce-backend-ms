package com.vantruong.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class ItemDto {
  @NotEmpty
  private int productId;
  @Min(value = 0, message = "Quantity must be non-negative")
  private int quantity;
}
