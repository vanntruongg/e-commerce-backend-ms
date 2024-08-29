package com.vantruong.product.service;

import com.vantruong.common.dto.response.CategoryResponse;
import com.vantruong.product.dto.AllLevelCategoryDto;
import com.vantruong.product.dto.CategoryDto;
import com.vantruong.product.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
  //  controler
  List<AllLevelCategoryDto> getALlCategory();

  CategoryResponse createCategory(CategoryDto categoryDto);

  List<CategoryResponse> getTopLevelCategory();

  List<CategoryResponse> getSubCategoriesByParentId(int parentId);

  List<AllLevelCategoryDto> getAllLevelChildrenByCategory(int categoryId);

  //  internal
  Optional<Category> getParentCategoryByCategoryId(int categoryId);

  List<Category> getAllLevelParentByCategory(int categoryId);

  Category getCategoryById(int id);

}
