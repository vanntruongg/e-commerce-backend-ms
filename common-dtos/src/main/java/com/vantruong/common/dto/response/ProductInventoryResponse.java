package com.vantruong.common.dto.response;

import com.vantruong.common.dto.SizeQuantityDto;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryResponse {
  private Map<Integer, List<SizeQuantityDto>> productInventoryResponse;
}
