package com.vantruong.product.dto;

import com.vantruong.product.entity.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllLevelCategoryDto {

  private Category category;
  private List<AllLevelCategoryDto> subCategories;
}
