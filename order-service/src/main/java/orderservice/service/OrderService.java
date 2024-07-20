package orderservice.service;

import orderservice.dto.OrderDto;
import orderservice.dto.OrderRequest;

import java.util.List;
import java.util.Map;

public interface OrderService {
  List<OrderDto> getAllOrder();

  OrderRequest createOrder(OrderRequest orderRequest);

  List<OrderDto> getOrderByStatus(String status);

  List<OrderDto> getOrderByEmailAndStatus(String email, String status);

  List<OrderDto> getOrderByEmail(String email);

  OrderDto getOrderById(int id);

  Boolean updateStatus(int id, String status);

}
