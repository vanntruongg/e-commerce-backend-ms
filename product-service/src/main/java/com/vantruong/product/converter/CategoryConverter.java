package com.vantruong.product.converter;

import com.vantruong.common.dto.response.CategoryResponse;
import com.vantruong.product.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryConverter {

  public CategoryResponse convertToCategoryResponse(Category category) {
    return CategoryResponse.builder()
            .id(category.getId())
            .name(category.getName())
            .image(category.getImage())
            .build();
  }

  public List<CategoryResponse> convertToListCategoryResponse(List<Category> categories) {
    return categories.stream().map(this::convertToCategoryResponse).toList();
  }


  //  public CategoryResponse convertToCategoryResponse(Category category) {
//    return convertToCategoryResponse(category, new HashSet<>());
//  }

//  private CategoryResponse convertToCategoryResponse(Category category, Set<Integer> visitedCategories) {
//    if (category == null || visitedCategories.contains(category.getId())) {
//      return null;
//    }
//
//    visitedCategories.add(category.getId());
//
//    return CategoryResponse.builder()
//            .id(category.getId())
//            .name(category.getName())
//            .image(category.getImage())
//            .parentCategory(
//                    convertToCategoryResponse(category.getParentCategory(), visitedCategories)
//            )
//            .build();
//  }
}
