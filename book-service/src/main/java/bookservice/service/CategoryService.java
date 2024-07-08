package bookservice.service;

import bookservice.dto.request.CategoryRequest;
import bookservice.dto.response.CategoryResponse;
import bookservice.entity.Category;

import java.util.List;

public interface CategoryService {
  Category createCategory(CategoryRequest categoryRequest);

  List<CategoryResponse> getALlCategory();
}
