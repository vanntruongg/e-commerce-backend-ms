package com.vantruong.product.entity.dto;

import lombok.*;
import com.vantruong.product.entity.Category;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
  private Category category;
  private List<CategoryResponse> subCategories;
}
