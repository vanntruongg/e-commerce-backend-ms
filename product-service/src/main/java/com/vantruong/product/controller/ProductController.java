package com.vantruong.product.controller;

import com.vantruong.product.common.CommonResponse;
import com.vantruong.product.constant.ApiEndpoint;
import com.vantruong.product.constant.MessageConstant;
import com.vantruong.product.dto.ProductPost;
import com.vantruong.product.dto.ProductPut;
import com.vantruong.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.product.constant.ApiEndpoint.GET_ALL;


@RestController
@RequestMapping(ApiEndpoint.PRODUCT)
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping(ApiEndpoint.CREATE_PRODUCT)
  public ResponseEntity<CommonResponse<Object>> createProduct(@RequestBody ProductPost productDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CREATE_PRODUCT_SUCCESS)
            .data(productService.createProduct(productDto))
            .build());
  }

  @GetMapping()
  public ResponseEntity<CommonResponse<Object>> getListProductByCustomer(
          @RequestParam(name = "category", defaultValue = "0", required = false) Long categoryId,
          @RequestParam(name = "sortOrder", required = false) String sortOrder,
          @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
          @RequestParam(name = "pageSize", defaultValue = "8", required = false) int pageSize
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getListProductByCustomer(categoryId, sortOrder, pageNo, pageSize))
            .build());
  }


  @GetMapping(GET_ALL)
  public ResponseEntity<CommonResponse<Object>> getAllProduct(
          @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
          @RequestParam(name = "pageSize", defaultValue = "8", required = false) int pageSize
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getListProduct(pageNo, pageSize))
            .build());
  }

  @PostMapping(ApiEndpoint.UPDATE_PRODUCT)
  public ResponseEntity<CommonResponse<Object>> updateProduct(@RequestBody ProductPut productPut) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_SUCCESS)
            .data(productService.updateProduct(productPut))
            .build());
  }

  @GetMapping(ApiEndpoint.PRODUCT_GET_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getProductById(@PathVariable("id") Long id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getProductById(id))
            .build());
  }

  @GetMapping(ApiEndpoint.PRODUCT_GET_BY_CATEGORY_ID)
  public ResponseEntity<CommonResponse<Object>> getProductsByCategoryId(
          @PathVariable("id") int id,
          @PathVariable("limit") int limit
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(productService.getProductsByCategoryId(id, limit))
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
