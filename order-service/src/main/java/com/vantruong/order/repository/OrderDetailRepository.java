package com.vantruong.order.repository;

import com.vantruong.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vantruong.order.entity.OrderDetail;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {
  List<OrderDetail> findAllByOrder(Order order);
}
