package vantruong.cartservice.entity;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {
  private int productId;
  @Min(value = 0, message = "Quantity must be non-negative")
  private int quantity;
}
