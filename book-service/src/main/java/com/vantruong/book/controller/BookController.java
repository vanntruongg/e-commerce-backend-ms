package com.vantruong.book.controller;

import com.vantruong.book.common.CommonResponse;
import com.vantruong.book.constant.MessageConstant;
import com.vantruong.book.dto.request.BooKRequest;
import com.vantruong.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.book.common.ApiEndpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
  private final BookService bookService;

  @GetMapping
  public ResponseEntity<CommonResponse<Object>> getAllProduct(
          @RequestParam(name = "category", defaultValue = "") String categoryId,
          @RequestParam(name = "order") String order,
          @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
          @RequestParam(name = "pageSize") int pageSize
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(bookService.findAllBook(categoryId, order, pageNo, pageSize))
            .build());
  }

  @PostMapping(CREATE)
  public ResponseEntity<CommonResponse<Object>> createProduct(@RequestBody BooKRequest booKDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CREATE_BOOK_SUCCESS)
            .data(bookService.createProduct(booKDto))
            .build());
  }

  @PostMapping(UPDATE)
  public ResponseEntity<CommonResponse<Object>> updateProduct(@RequestBody BooKRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_SUCCESS)
            .data(bookService.updateProduct(request))
            .build());
  }

  @GetMapping(GET_STOCK_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getStockById(@PathVariable("id") String id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(bookService.getStockById(id))
            .build());
  }

  @GetMapping(GET_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getProductById(@PathVariable("id") String id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(bookService.getProductById(id))
            .build());
  }

  @GetMapping(GET_BY_NAME)
  public ResponseEntity<CommonResponse<Object>> getProductByName(
          @RequestParam("name") String name,
          @RequestParam(value = "limit", defaultValue = "10") int limit
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(bookService.getProductByName(name, limit))
            .build());
  }

  @GetMapping(GET_BY_CATEGORY_ID)
  public ResponseEntity<CommonResponse<Object>> getProductsByCategoryId(
          @PathVariable("id") String id,
          @PathVariable(value = "limit", name = "10") int limit
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(bookService.findAllBookByCategoryId(id, limit))
            .build());
  }

  @GetMapping(COUNT)
  public ResponseEntity<CommonResponse<Object>> getTotalBooksCount() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(bookService.getTotalBooksCount())
            .build());
  }
}
