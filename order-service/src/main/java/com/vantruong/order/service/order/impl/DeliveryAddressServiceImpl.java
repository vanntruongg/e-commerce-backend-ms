package com.vantruong.order.service.order.impl;

import com.vantruong.order.entity.DeliveryAddress;
import com.vantruong.order.entity.Order;
import com.vantruong.order.repository.DeliveryAddressRepository;
import com.vantruong.order.service.order.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
  private final DeliveryAddressRepository deliveryAddressRepository;

  @Override
  public void createDeliveryAddress(String name, String phone, String address, Order order) {
    DeliveryAddress deliveryAddress = DeliveryAddress.builder()
            .name(name)
            .phone(phone)
            .address(address)
            .order(order)
            .build();
    deliveryAddressRepository.save(deliveryAddress);
  }

  @Override
  public DeliveryAddress getByOrder(Order order) {
    return deliveryAddressRepository.findByOrder(order);
  }
}
