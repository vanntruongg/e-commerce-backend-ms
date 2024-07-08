package productservice.service;

import productservice.entity.Category;
import productservice.entity.dto.CategoryDto;
import productservice.entity.dto.CategoryResponse;

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
