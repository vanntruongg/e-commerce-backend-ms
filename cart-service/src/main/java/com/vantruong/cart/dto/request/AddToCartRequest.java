package com.vantruong.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AddToCartRequest {
  @NotNull
  private Long productId;
  private String size;
  @Min(value = 0, message = "Quantity must be non-negative")
  private int quantity;
}
