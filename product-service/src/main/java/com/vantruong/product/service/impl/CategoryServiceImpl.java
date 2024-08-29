package com.vantruong.product.service.impl;

import com.vantruong.common.dto.response.CategoryResponse;
import com.vantruong.common.exception.ErrorCode;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.product.constant.MessageConstant;
import com.vantruong.product.converter.CategoryConverter;
import com.vantruong.product.dto.AllLevelCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.vantruong.product.entity.Category;
import com.vantruong.product.dto.CategoryDto;
import com.vantruong.product.repository.CategoryRepository;
import com.vantruong.product.service.CategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryConverter categoryConverter;

  @Override
  public CategoryResponse createCategory(CategoryDto categoryDto) {
    Category category = Category.builder()
            .name(categoryDto.getName())
            .image(categoryDto.getImage())
            .parentCategory(categoryDto.getParentCategory())
            .build();
    categoryRepository.save(category);
    return categoryConverter.convertToCategoryResponse(category);
  }

  @Override
  public List<AllLevelCategoryDto> getALlCategory() {
    List<Category> categories = categoryRepository.findTopLevelCategory();
    List<AllLevelCategoryDto> result = new ArrayList<>();
    for (Category category : categories) {
      List<AllLevelCategoryDto> categoryResponses = getAllLevelChildrenByCategory(category.getId());
      AllLevelCategoryDto categoryResponse = AllLevelCategoryDto.builder()
              .category(category)
              .subCategories(categoryResponses)
              .build();
      result.add(categoryResponse);
    }
    return result;
  }

  @Override
  public List<CategoryResponse> getTopLevelCategory() {
    List<Category> categories = categoryRepository.findTopLevelCategory();
    return categoryConverter.convertToListCategoryResponse(categories);
  }

  @Override
  public List<CategoryResponse> getSubCategoriesByParentId(int parentId) {
    List<Category> categories = categoryRepository.findSubcategoriesByParentId(parentId);
    return categoryConverter.convertToListCategoryResponse(categories);
  }

  private List<Category> findSubCategoriesByParentId(int parentId) {
    return categoryRepository.findSubcategoriesByParentId(parentId);
  }

  @Override
  public Optional<Category> getParentCategoryByCategoryId(int categoryId) {
    return categoryRepository.findParentCategoryByCategoryId(categoryId);
  }

  @Override
  public List<Category> getAllLevelParentByCategory(int categoryId) {
    List<Category> categories = new ArrayList<>();
    Category category = getCategoryById(categoryId);
    categories.add(category);
    Optional<Category> existedParentCategory = getParentCategoryByCategoryId(category.getId());
    existedParentCategory.ifPresent(value -> categories.addAll(getAllLevelParentByCategory(value.getId())));
    return categories;
  }

  @Override
  public Category getCategoryById(int id) {
    return categoryRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.CATEGORY_NOT_FOUND));
  }

  @Override
  public List<AllLevelCategoryDto> getAllLevelChildrenByCategory(int categoryId) {
    List<AllLevelCategoryDto> result = new ArrayList<>();
    getAllChildrenByCategoryRecursive(categoryId, result);
    return result;
  }


  private void getAllChildrenByCategoryRecursive(int categoryId, List<AllLevelCategoryDto> result) {
    List<Category> children = findSubCategoriesByParentId(categoryId);

    /* Each time the recursive call is made,
      the list of categories is set to the subcategory */
    for (Category child : children) {
      AllLevelCategoryDto categoryResponse = new AllLevelCategoryDto();
      categoryResponse.setCategory(child);
      List<AllLevelCategoryDto> subCategories = new ArrayList<>();
      categoryResponse.setSubCategories(subCategories);
      result.add(categoryResponse);
      getAllChildrenByCategoryRecursive(child.getId(), subCategories);
    }
  }

}
