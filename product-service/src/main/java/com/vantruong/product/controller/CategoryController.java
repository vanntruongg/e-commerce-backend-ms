package com.vantruong.product.controller;

import com.vantruong.product.common.CommonResponse;
import com.vantruong.product.constant.ApiEndpoint;
import com.vantruong.product.constant.MessageConstant;
import com.vantruong.product.dto.CategoryPost;
import com.vantruong.product.dto.CategoryPut;
import com.vantruong.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.product.constant.ApiEndpoint.GET_ALL_PARENT_CATEGORY_BY_ID;

@RestController
@RequestMapping(ApiEndpoint.PRODUCT + ApiEndpoint.CATEGORY)
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;


  @GetMapping()
  public ResponseEntity<CommonResponse<Object>> getAllCategory() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getALlCategory())
            .build());
  }

  @GetMapping(GET_ALL_PARENT_CATEGORY_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getAllParentCategoryById(@PathVariable("id") Long categoryId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(categoryService.getAllLevelParentByCategoryId(categoryId))
            .build());
  }

  @PostMapping(ApiEndpoint.CREATE)
  public ResponseEntity<CommonResponse<Object>> createCategory(@RequestBody CategoryPost categoryPost) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CREATE_CATEGORY_SUCCESS)
            .data(categoryService.createCategory(categoryPost))
            .build());
  }

  @PutMapping(ApiEndpoint.UPDATE)
  public ResponseEntity<CommonResponse<Object>> updateCategory(@RequestBody CategoryPut categoryPut) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_CATEGORY_SUCCESS)
            .data(categoryService.updateCategory(categoryPut))
            .build());
  }


//  @GetMapping(ApiEndpoint.GET_TOP_LEVEL_CATEGORY)
//  public ResponseEntity<CommonResponse<Object>> getTopLevelCategory() {
//    return ResponseEntity.ok().body(CommonResponse.builder()
//            .isSuccess(true)
//            .message(MessageConstant.FIND_SUCCESS)
//            .data(categoryService.getTopLevelCategory())
//            .build());
//  }
//
//
//  @GetMapping(ApiEndpoint.CATEGORY_GET_SUBCATEGORY)
//  public ResponseEntity<CommonResponse<Object>> getSubCategoriesByParentId(@PathVariable("id") Long parentId) {
//    return ResponseEntity.ok().body(CommonResponse.builder()
//            .isSuccess(true)
//            .message(MessageConstant.FIND_SUCCESS)
//            .data(categoryService.getSubCategoriesByParentId(parentId))
//            .build());
//  }
//
//  @GetMapping(ApiEndpoint.CATEGORY_GET_SUBCATEGORY_ALL_LEVEL)
//  public ResponseEntity<CommonResponse<Object>> getAllLevelSubCategoriesByParentId(@PathVariable("id") Long categoryId) {
//    return ResponseEntity.ok().body(CommonResponse.builder()
//            .isSuccess(true)
//            .message(MessageConstant.FIND_SUCCESS)
//            .data(categoryService.getAllLevelChildrenByCategory(categoryId))
//            .build());
//  }
}
