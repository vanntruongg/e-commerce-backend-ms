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
  public ResponseEntity<CommonResponse<Object>> getAllOrder(
          @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
          @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
          @RequestParam(value = "orderStatus", defaultValue = "") String orderStatus,
          @RequestParam(value = "paymentMethod", defaultValue = "") String paymentMethod
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getAllOrderByAdmin(pageNo, pageSize, orderStatus, paymentMethod))
            .build());
  }

  @GetMapping(ApiEndpoint.SEARCH_BY_ID)
  public ResponseEntity<CommonResponse<Object>> searchById(
          @PathVariable(name = "id") Long orderId
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.searchById(orderId))
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

  @GetMapping(ApiEndpoint.GET_MY_ORDER)
  public ResponseEntity<CommonResponse<Object>> getAllMyOrder(
          @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
          @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
          @RequestParam(value = "orderStatus", defaultValue = "") String orderStatus
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(orderService.getAllMyOrder(pageNo, pageSize, orderStatus))
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
