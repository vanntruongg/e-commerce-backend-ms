package com.vantruong.book.service.impl;

import com.vantruong.book.dto.request.CategoryRequest;
import com.vantruong.book.dto.response.CategoryResponse;
import com.vantruong.book.entity.Category;
import com.vantruong.book.repository.CategoryRepository;
import com.vantruong.book.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;

  @Override
  public Category createCategory(CategoryRequest categoryRequest) {
    Category category = Category.builder()
            .category(categoryRequest.getName())
            .build();
    return categoryRepository.save(category);
  }

  @Override
  public List<CategoryResponse> getALlCategory() {
    List<Category> categories = categoryRepository.findAll();
    List<CategoryResponse> result = new ArrayList<>();
    for (Category category : categories) {
      CategoryResponse categoryResponse = CategoryResponse.builder()
              .category(category.getCategory())
              .build();
      result.add(categoryResponse);
    }
    return result;
  }
}
