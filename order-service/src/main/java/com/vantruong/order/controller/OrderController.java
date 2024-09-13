package com.vantruong.order.controller;

import com.vantruong.order.common.CommonResponse;
import com.vantruong.order.constant.ApiEndpoint;
import com.vantruong.order.constant.MessageConstant;
import com.vantruong.order.dto.OrderRequest;
import com.vantruong.order.entity.enumeration.OrderStatus;
import com.vantruong.order.service.OrderService;
import com.vantruong.order.service.PaymentOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoint.ORDER)
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;
  private final PaymentOrderService paymentOrderService;


  @GetMapping(ApiEndpoint.ORDERS)
  public ResponseEntity<CommonResponse<Object>> getAllOrder() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getAllOrder())
            .build());
  }

  @PostMapping(ApiEndpoint.CREATE_ORDER)
  public ResponseEntity<CommonResponse<Object>> createOrder(@RequestBody OrderRequest orderRequest) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.ORDER_SUCCESS)
            .data(paymentOrderService.placeOrder(orderRequest))
            .build());
  }

  @GetMapping(ApiEndpoint.GET_BY_USER)
  public ResponseEntity<CommonResponse<Object>> getOrderByUser() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getOrderByUser())
            .build());
  }

  @GetMapping(ApiEndpoint.GET_BY_ID)
  public ResponseEntity<CommonResponse<Object>> getOrderById(@PathVariable("id") Long id) {
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

//  @GetMapping(ApiEndpoint.GET_BY_USER_AND_STATUS)
//  public ResponseEntity<CommonResponse<Object>> getOrderOfUserByStatus(@RequestParam("status") String status) {
//    return ResponseEntity.ok().body(CommonResponse.builder()
//            .isSuccess(true)
//            .message(MessageConstant.FIND_SUCCESS)
//            .data(orderService.getOrderOfUserByStatus(status))
//            .build());
//  }

  @PostMapping(ApiEndpoint.UPDATE_STATUS)
  public ResponseEntity<CommonResponse<Object>> updateStatus(
          @RequestParam("id") Long id,
          @RequestParam("status") String status) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_SUCCESS)
            .data(orderService.updateOrderStatus(id, OrderStatus.findOrderStatus(status)))
            .build());
  }

}
