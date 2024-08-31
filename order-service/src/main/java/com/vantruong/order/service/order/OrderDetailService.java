package com.vantruong.order.service.order;

import com.vantruong.order.dto.OrderDetailRequest;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
  List<OrderDetail> createOrderDetails(Order order, List<OrderDetailRequest> orderDetailDTOs);

//  List<OrderDetailDto> getAllOrderDetailByOrder(Order order);
}
