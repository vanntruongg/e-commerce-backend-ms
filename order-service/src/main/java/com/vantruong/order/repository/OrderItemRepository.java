package com.vantruong.order.repository;

import com.vantruong.common.dto.product.ProductSoldResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vantruong.order.entity.OrderItem;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

  @Query("select oi.productId, sum (oi.quantity) " +
          "from OrderItem oi " +
          "group by oi.productId")
  List<Object[]> getTotalQuantityPerProduct();
}
