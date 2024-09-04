package com.vantruong.order.service.order;

import com.vantruong.order.entity.DeliveryAddress;
import com.vantruong.order.entity.Order;

public interface DeliveryAddressService {

  void createDeliveryAddress(String name, String phone, String address, Order order);
  DeliveryAddress getByOrder(Order order);
}
