package com.vantruong.inventory.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SizeQuantity {
  private String size;
  private Integer quantity;
}
