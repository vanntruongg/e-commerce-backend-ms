package com.vantruong.book.controller;

import com.vantruong.book.common.CommonResponse;
import com.vantruong.book.common.InernalApiEndpoint;
import com.vantruong.book.constant.MessageConstant;
import com.vantruong.book.service.BookService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RestController
@RequestMapping(InernalApiEndpoint.INTERNAL + InernalApiEndpoint.PRODUCT)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalBookController {
  BookService bookService;

  @GetMapping(InernalApiEndpoint.GET_STOCK_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getStockById(@PathVariable("id") String id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(bookService.getStockById(id))
            .build());
  }

  @PostMapping(InernalApiEndpoint.UPDATE_QUANTITY)
  public ResponseEntity<Object> updateProductQuantityByOrder(@RequestBody Map<String, Integer> stockUpdate) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(false)
            .message(MessageConstant.INSUFFICIENT_PRODUCT_QUANTITY)
            .data(bookService.updateProductQuantityByOrder(stockUpdate))
            .build());
  }

  @PostMapping(InernalApiEndpoint.GET_BY_IDS)
  public ResponseEntity<CommonResponse<Object>> getProductsByIds(@RequestBody List<String> productIds) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(bookService.getProductsByIds(productIds))
            .build());
  }

}
