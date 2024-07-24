package com.vantruong.book.controller;

import com.vantruong.book.common.CommonResponse;
import com.vantruong.book.constant.MessageConstant;
import com.vantruong.book.dto.request.CategoryRequest;
import com.vantruong.book.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.book.common.ApiEndpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BOOK + CATEGORY)
public class CategoryController {
  private final CategoryService categoryService;

  @PostMapping(CREATE)
  public ResponseEntity<CommonResponse<Object>> createCategory(@RequestBody CategoryRequest categoryRequest) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CREATE_CATEGORY_SUCCESS)
            .data(categoryService.createCategory(categoryRequest))
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
}
