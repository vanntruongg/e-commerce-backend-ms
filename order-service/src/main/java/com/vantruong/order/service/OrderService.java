package com.vantruong.order.service;

import com.vantruong.order.dto.OrderDto;
import com.vantruong.order.dto.OrderRequest;

import java.util.List;

public interface OrderService {
  List<OrderDto> getAllOrder();

  OrderRequest placeOrder(OrderRequest orderRequest);

  List<OrderDto> getOrderByStatus(String status);

  List<OrderDto> getOrderByEmailAndStatus(String email, String status);

  List<OrderDto> getOrderByEmail(String email);

  OrderDto getOrderById(int id);

  Boolean updateStatus(int id, String status);

}
