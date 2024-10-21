package com.vantruong.common.dto.inventory;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SizeQuantityDto {
  private String size;
  private Integer quantity;
}
