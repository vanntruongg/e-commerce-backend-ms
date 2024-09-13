package com.vantruong.order.repository;

import com.vantruong.order.entity.OrderAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAddressRepository extends JpaRepository<OrderAddress, Integer> {

}
