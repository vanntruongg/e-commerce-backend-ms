package com.vantruong.order.repository;

import com.vantruong.order.entity.DeliveryAddress;
import com.vantruong.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Integer> {

  DeliveryAddress findByOrder(Order order);
}
