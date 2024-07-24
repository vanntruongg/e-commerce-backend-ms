package com.vantruong.product.service;

import com.vantruong.product.entity.dto.CategoryDto;
import com.vantruong.product.entity.dto.CategoryResponse;
import com.vantruong.product.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
  List<CategoryResponse> getALlCategory();

  List<Category> getSubCategoriesByParentId(int parentId);
  Optional<Category> getParentCategoryByCategoryId(int categoryId);

  List<Category> getAllLevelParentByCategory(int categoryId);

  Category getCategoryById(int id);

  List<CategoryResponse> getAllLevelChildrenByCategory(int categoryId);

  List<Category> getTopLevelCategory();

  Category createCategory(CategoryDto categoryDto);
}
