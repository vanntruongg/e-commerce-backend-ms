package com.vantruong.common.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryRequest {
  private List<Integer> productIds;
}
