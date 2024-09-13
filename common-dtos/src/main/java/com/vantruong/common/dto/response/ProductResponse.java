package com.vantruong.common.dto.response;

import com.vantruong.common.dto.inventory.SizeQuantityDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ProductResponse {
  private Long id;
  private String name;
  private Double price;
  private String material;
  private String style;
  private CategoryResponse category;
  private List<ProductImageResponse> images;
  private List<SizeQuantityDto> sizeQuantity;
//  private List<CategoryResponse> categories;
}
