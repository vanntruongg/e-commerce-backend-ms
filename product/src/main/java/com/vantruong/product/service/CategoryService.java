package com.vantruong.product.service;

import com.vantruong.product.constant.MessageConstant;
import com.vantruong.product.converter.CategoryConverter;
import com.vantruong.product.dto.AllLevelCategoryDto;
import com.vantruong.product.dto.CategoryPost;
import com.vantruong.product.dto.CategoryPut;
import com.vantruong.product.entity.Category;
import com.vantruong.product.exception.ErrorCode;
import com.vantruong.product.exception.NotFoundException;
import com.vantruong.product.repository.CategoryRepository;
import com.vantruong.product.viewmodel.CategoryVm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryConverter categoryConverter;


  private Category findById(Long categoryId) {
    if (categoryId == null) return null;
    return categoryRepository.findById(categoryId)
            .orElse(null);
  }

  public CategoryVm createCategory(CategoryPost categoryPost) {
    Category parentCategory = this.findById(categoryPost.parentCategoryId());
    Category category = Category.builder()
            .name(categoryPost.name())
            .image(categoryPost.imageUrl())
            .parentCategory(parentCategory)
            .build();
    categoryRepository.save(category);
    return categoryConverter.convertToCategoryResponse(category);
  }

  public Boolean updateCategory(CategoryPut categoryPut) {
    Category category = this.findById(categoryPut.categoryId());
    category.setName(categoryPut.name());
    category.setImage(categoryPut.imageUrl());

    if (categoryPut.isTopLevel()) {
      category.setParentCategory(null);
    } else {
      Category parentCategory = this.findById(categoryPut.parentCategoryId());
      if (parentCategory != null && !parentCategory.getId().equals(category.getId())) {
        category.setParentCategory(parentCategory);
      }
    }

    categoryRepository.save(category);
    return true;
  }

  public List<AllLevelCategoryDto> getALlCategory() {
    List<Category> categories = categoryRepository.findTopLevelCategory(Sort.Order.asc("name"));
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

  private List<Category> findSubCategoriesByParentId(Long parentId) {
    return categoryRepository.findSubcategoriesByParentId(parentId);
  }

  public Optional<Category> getParentCategoryByCategoryId(Long categoryId) {
    return categoryRepository.findParentCategoryByCategoryId(categoryId);
  }

  public List<Category> getAllLevelParentByCategoryId(Long categoryId) {
    List<Category> categories = new ArrayList<>();
    Category category = getCategoryById(categoryId);
    categories.add(category);
    Optional<Category> existedParentCategory = getParentCategoryByCategoryId(category.getId());
    existedParentCategory.ifPresent(value -> categories.addAll(getAllLevelParentByCategoryId(value.getId())));
    return categories;
  }

  public Category getCategoryById(Long id) {
    return categoryRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.CATEGORY_NOT_FOUND));
  }

  public List<AllLevelCategoryDto> getAllLevelChildrenByCategory(Long categoryId) {
    List<AllLevelCategoryDto> result = new ArrayList<>();
    getAllChildrenByCategoryRecursive(categoryId, result);
    return result;
  }


  private void getAllChildrenByCategoryRecursive(Long categoryId, List<AllLevelCategoryDto> result) {
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

  public List<Long> getAllSubCategoryIds(Long categoryId) {
    List<Long> categoryIds = new ArrayList<>();
    findSubCategoryIdsRecursively(categoryId, categoryIds);
    return categoryIds;
  }

  private void findSubCategoryIdsRecursively(Long parentId, List<Long> categoryIds) {
    List<Category> categories = findSubCategoriesByParentId(parentId);
    for (Category category : categories) {
      categoryIds.add(category.getId());
      findSubCategoryIdsRecursively(category.getId(), categoryIds);
    }
  }


}
