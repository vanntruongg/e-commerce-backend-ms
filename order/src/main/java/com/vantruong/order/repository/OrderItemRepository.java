package com.vantruong.order.repository;

import com.vantruong.order.entity.OrderItem;
import com.vantruong.order.viewmodel.ProductSoldVm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

  @Query("select new com.vantruong.order.viewmodel.ProductSoldVm(oi.productId, sum (oi.quantity)) " +
          "from OrderItem oi " +
          "group by oi.productId")
  List<ProductSoldVm> getTotalQuantityPerProduct();
}
