package com.vantruong.book.service;

import com.vantruong.book.dto.request.CategoryRequest;
import com.vantruong.book.dto.response.CategoryResponse;
import com.vantruong.book.entity.Category;

import java.util.List;

public interface CategoryService {
  Category createCategory(CategoryRequest categoryRequest);

  List<CategoryResponse> getALlCategory();
}
