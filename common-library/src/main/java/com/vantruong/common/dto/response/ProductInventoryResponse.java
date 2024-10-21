package com.vantruong.common.dto.response;

import com.vantruong.common.dto.inventory.SizeQuantityDto;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryResponse {
  private Map<Long, List<SizeQuantityDto>> productInventoryResponse;
}
