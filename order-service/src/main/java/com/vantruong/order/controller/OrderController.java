package com.vantruong.order.controller;

import com.vantruong.order.constant.ApiEndpoint;
import com.vantruong.order.constant.MessageConstant;
import lombok.RequiredArgsConstructor;
import com.vantruong.order.common.CommonResponse;
import com.vantruong.order.dto.OrderRequest;
import com.vantruong.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoint.ORDER)
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @GetMapping(ApiEndpoint.ORDERS)
  public ResponseEntity<CommonResponse<Object>> getAllOrder() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND)
            .data(orderService.getAllOrder())
            .build());
  }

  @PostMapping(ApiEndpoint.CREATE_ORDER)
  public ResponseEntity<CommonResponse<Object>> createOrder(@RequestBody OrderRequest orderRequest) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.ORDER_SUCCESS)
            .data(orderService.placeOrder(orderRequest))
            .build());
  }

  @GetMapping(ApiEndpoint.GET_BY_EMAIL)
  public ResponseEntity<CommonResponse<Object>> getOrderByEmail(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getOrderByEmail(email))
            .build());
  }

  @GetMapping(ApiEndpoint.GET_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getOrderById(@PathVariable("id") int id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getOrderById(id))
            .build());
  }

  @GetMapping(ApiEndpoint.GET_BY_STATUS)
  public ResponseEntity<CommonResponse<Object>> getOrderByStatus(@RequestParam("status") String status) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getOrderByStatus(status))
            .build());
  }

  @GetMapping(ApiEndpoint.GET_BY_EMAIL_AND_STATUS)
  public ResponseEntity<CommonResponse<Object>> getOrderByEmailAndStatus(
          @RequestParam("email") String email,
          @RequestParam("status") String status) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getOrderByEmailAndStatus(email, status))
            .build());
  }

  @PostMapping(ApiEndpoint.UPDATE_STATUS)
  public ResponseEntity<CommonResponse<Object>> updateStatus(
          @RequestParam("id") int id,
          @RequestParam("status") String status) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_SUCCESS)
            .data(orderService.updateStatus(id, status))
            .build());
  }

}
