package com.vantruong.product.controller;

import com.vantruong.product.common.CommonResponse;
import com.vantruong.product.constant.InernalApiEndpoint;
import com.vantruong.product.constant.MessageConstant;
import com.vantruong.product.converter.ProductConverter;
import com.vantruong.product.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.vantruong.common.constant.InternalApiEndpoint.CALCULATE_BY_PRODUCT_IDS;
import static com.vantruong.common.constant.InternalApiEndpoint.GET_BY_PRODUCT_ID;


@RestController
@RequestMapping(InernalApiEndpoint.INTERNAL + InernalApiEndpoint.PRODUCT)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalProductController {
  ProductService productService;
  ProductConverter productConverter;

  @PostMapping(InernalApiEndpoint.GET_BY_IDS)
  public ResponseEntity<CommonResponse<Object>> getProductsByIds(@RequestBody List<Integer> productIds) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(productService.getProductsByIds(productIds))
            .build());
  }


  @PostMapping(GET_BY_PRODUCT_ID)
  public ResponseEntity<CommonResponse<Object>> getProductsById(@PathVariable("id") Integer id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(productConverter.convertToProductResponse(productService.getProductById(id)))
            .build());
  }

  @PostMapping(CALCULATE_BY_PRODUCT_IDS)
  public ResponseEntity<CommonResponse<Object>> calculateTotalPriceByProductIds(Map<Integer, Integer> productQuantities) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(productService.calculateTotalPriceByProductIds(productQuantities))
            .build());
  }



}
