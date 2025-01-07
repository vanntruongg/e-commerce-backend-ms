package com.vantruong.product.converter;

import com.vantruong.product.entity.Category;
import com.vantruong.product.viewmodel.CategoryVm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryConverter {

  public CategoryVm convertToCategoryResponse(Category category) {
      return new CategoryVm(
            category.getId(),
            category.getName(),
            category.getImage()
    );
  }

  public List<CategoryVm> convertToListCategoryResponse(List<Category> categories) {
    return categories.stream().map(this::convertToCategoryResponse).toList();
  }

}
