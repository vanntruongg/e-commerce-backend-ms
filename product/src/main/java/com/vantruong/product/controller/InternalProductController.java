package com.vantruong.product.controller;

import com.vantruong.product.common.CommonResponse;
import com.vantruong.product.constant.InternalApiEndpoint;
import com.vantruong.product.constant.MessageConstant;
import com.vantruong.product.converter.ProductConverter;
import com.vantruong.product.service.ProductService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.vantruong.product.constant.InternalApiEndpoint.CALCULATE_BY_PRODUCT_IDS;
import static com.vantruong.product.constant.InternalApiEndpoint.GET_BY_PRODUCT_ID;


@RestController
@RequestMapping(InternalApiEndpoint.INTERNAL + InternalApiEndpoint.PRODUCT)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalProductController {
  ProductService productService;
  ProductConverter productConverter;

  public InternalProductController(ProductService productService, ProductConverter productConverter) {
    this.productService = productService;
    this.productConverter = productConverter;
  }

  @PostMapping(InternalApiEndpoint.GET_BY_IDS)
  public ResponseEntity<CommonResponse<Object>> getProductsByIds(@RequestBody List<Long> productIds) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(productService.getProductsByIds(productIds))
            .build());
  }


  @PostMapping(GET_BY_PRODUCT_ID)
  public ResponseEntity<CommonResponse<Object>> getProductsById(@PathVariable("id") Long id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(productConverter.convertToProductResponse(productService.getProductById(id)))
            .build());
  }

  @PostMapping(CALCULATE_BY_PRODUCT_IDS)
  public ResponseEntity<CommonResponse<Object>> calculateTotalOrderPrice(@RequestBody Map<Long, Integer> productQuantities) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(productService.calculateTotalOrderPrice(productQuantities))
            .build());
  }


}
