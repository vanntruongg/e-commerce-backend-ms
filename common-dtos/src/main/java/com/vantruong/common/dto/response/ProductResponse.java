package com.vantruong.common.dto.response;

import com.vantruong.common.dto.SizeQuantityDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ProductResponse {
  private int id;
  private String name;
  private double price;
  private String material;
  private String style;
  private CategoryResponse category;
  private List<ProductImageResponse> images;
  private List<CategoryResponse> categories;
  private List<SizeQuantityDto> sizeQuantity;
}
