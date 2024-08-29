package com.vantruong.order.service.order;

import com.vantruong.order.dto.OrderDetailDto;
import com.vantruong.order.entity.OrderDetail;
import com.vantruong.order.entity.Order;

import java.util.List;

public interface OrderDetailService {
  List<OrderDetail> createOrderDetails(Order order, List<OrderDetailDto> orderDetailDTOs);

//  List<OrderDetailDto> getAllOrderDetailByOrder(Order order);
}
