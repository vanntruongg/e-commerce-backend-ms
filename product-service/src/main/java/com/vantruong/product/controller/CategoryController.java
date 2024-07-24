package com.vantruong.product.controller;

import com.vantruong.product.constant.ApiEndpoint;
import com.vantruong.product.constant.MessageConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vantruong.product.service.CategoryService;
import com.vantruong.product.common.CommonResponse;
import com.vantruong.product.entity.dto.CategoryDto;

@RestController
@RequestMapping(ApiEndpoint.PRODUCT + ApiEndpoint.CATEGORY)
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @PostMapping(ApiEndpoint.CATEGORY_CREATE)
  public ResponseEntity<CommonResponse<Object>> createCategory(@RequestBody CategoryDto categoryDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CREATE_CATEGORY_SUCCESS)
            .data(categoryService.createCategory(categoryDto))
            .build());
  }

  @GetMapping
  public ResponseEntity<CommonResponse<Object>> getALlCategory() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getALlCategory())
            .build());
  }

  @GetMapping(ApiEndpoint.GET_TOP_LEVEL_CATEGORY)
  public ResponseEntity<CommonResponse<Object>> getTopLevelCategory() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getTopLevelCategory())
            .build());
  }


  @GetMapping(ApiEndpoint.CATEGORY_GET_SUBCATEGORY)
  public ResponseEntity<CommonResponse<Object>> getSubCategoriesByParentId(@PathVariable("id") int parentId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getSubCategoriesByParentId(parentId))
            .build());
  }

  @GetMapping(ApiEndpoint.CATEGORY_GET_SUBCATEGORY_ALL_LEVEL)
  public ResponseEntity<CommonResponse<Object>> getAllLevelSubCategoriesByParentId(@PathVariable("id") int categoryId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getAllLevelChildrenByCategory(categoryId))
            .build());
  }
}
