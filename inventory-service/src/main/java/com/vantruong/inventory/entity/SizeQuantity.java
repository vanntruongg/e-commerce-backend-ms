package com.vantruong.inventory.entity;


import com.vantruong.inventory.enums.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeQuantity {
  private Size size;
  private Integer quantity;
}
