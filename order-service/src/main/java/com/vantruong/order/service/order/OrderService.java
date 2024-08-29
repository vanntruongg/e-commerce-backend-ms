package com.vantruong.order.service.order;

import com.vantruong.order.dto.OrderDto;
import com.vantruong.order.dto.OrderRequest;
import com.vantruong.order.entity.Order;
import com.vantruong.order.enums.OrderStatus;
import com.vantruong.order.enums.PaymentStatus;

import java.util.List;

public interface OrderService {
  List<OrderDto> getAllOrder();

  List<OrderDto> getOrderByStatus(String status);

  List<OrderDto> getOrderByEmailAndStatus(String email, String status);

  List<OrderDto> getOrderByEmail(String email);

  Order createNewOrder(OrderRequest orderRequest);

  OrderDto getOrderById(int id);
  Order findById(int id);

  Boolean updateOrderStatus(int id, OrderStatus status);
  void updatePaymentStatus(int id, PaymentStatus status);
   void deleteOrder(Integer orderId);

}
