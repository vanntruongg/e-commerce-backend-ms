package com.vantruong.product.controller;

import com.vantruong.product.constant.InernalApiEndpoint;
import com.vantruong.product.constant.MessageConstant;
import com.vantruong.product.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vantruong.product.common.CommonResponse;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(InernalApiEndpoint.INTERNAL + InernalApiEndpoint.PRODUCT)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalProductController {
  ProductService productService;

  @PostMapping(InernalApiEndpoint.GET_BY_IDS)
  public ResponseEntity<CommonResponse<Object>> getProductsByIds(@RequestBody List<Integer> productIds) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(productService.getProductsByIds(productIds))
            .build());
  }

}
