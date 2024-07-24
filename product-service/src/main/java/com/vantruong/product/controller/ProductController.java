package com.vantruong.product.controller;

import com.vantruong.product.constant.ApiEndpoint;
import com.vantruong.product.constant.MessageConstant;
import com.vantruong.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vantruong.product.common.CommonResponse;
import com.vantruong.product.entity.dto.ProductDto;


@RestController
@RequestMapping(ApiEndpoint.PRODUCT)
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;
  @PostMapping(ApiEndpoint.CREATE_PRODUCT)
  public ResponseEntity<CommonResponse<Object>> createProduct(@RequestBody ProductDto productDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CREATE_PRODUCT_SUCCESS)
            .data(productService.createProduct(productDto))
            .build());
  }

  @GetMapping
  public ResponseEntity<CommonResponse<Object>> getAllProduct(
          @RequestParam(name = "category", defaultValue = "0") int categoryId,
          @RequestParam(name = "order") String order,
          @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
          @RequestParam(name = "pageSize") int pageSize
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getAllProduct(categoryId, order, pageNo, pageSize))
            .build());
  }

  @PostMapping(ApiEndpoint.UPDATE_PRODUCT)
  public ResponseEntity<CommonResponse<Object>> updateProduct(@RequestBody ProductDto productDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_SUCCESS)
            .data(productService.updateProduct(productDto))
            .build());
  }

  @GetMapping(ApiEndpoint.PRODUCT_GET_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getProductWithCategoryById(@PathVariable("id") int id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getProductWithCategoryById(id))
            .build());
  }

  @GetMapping(ApiEndpoint.PRODUCT_GET_BY_CATEGORY_ID)
  public ResponseEntity<CommonResponse<Object>> getProductsByCategoryId(@PathVariable("id") int id, @PathVariable("limit") int limit) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getProductsByCategoryId(id, limit))
            .build());
  }
  @GetMapping(ApiEndpoint.PRODUCT_GET_STOCK_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getStockById(@PathVariable("id") int id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getStockById(id))
            .build());
  }

  @GetMapping(ApiEndpoint.PRODUCT_GET_BY_NAME)
  public ResponseEntity<CommonResponse<Object>> getProductByName(
          @RequestParam("name") String name,
          @RequestParam("limit") int limit
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.findProductByName(name, limit))
            .build());
  }

  @GetMapping(ApiEndpoint.COUNT_PRODUCT)
  public ResponseEntity<CommonResponse<Object>> getProductCount() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getProductCount())
            .build());
  }
}
