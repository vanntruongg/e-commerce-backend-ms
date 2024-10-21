package com.vantruong.order.repository;

import com.vantruong.order.entity.enumeration.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vantruong.order.entity.Order;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
  List<Order> findOrderByOrderStatus(OrderStatus status);

  Page<Order> findAllByOrderStatus(OrderStatus orderStatus, Pageable pageable);

  @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END " +
          "from Order o inner join OrderItem oi on o.orderId = oi.order.orderId " +
          "where o.email = :email " +
          "and o.orderStatus = :orderStatus " +
          "and oi.productId = :productId")
  boolean existsByEmailAndProductIdAndOrderStatus(String email, Long productId, OrderStatus orderStatus);

  Page<Order> findByEmail(String email, Pageable pageable);
  Page<Order> findByEmailAndOrderStatus(String email, OrderStatus orderStatus, Pageable pageable);

  @Query("select o.orderStatus, count(o) from Order o group by o.orderStatus")
  List<Object[]> findOrderCountByStatus();

  @Query("select extract(month from o.createdDate), count(*) " +
          "from Order o " +
          "where extract(year from o.createdDate) = extract(year from current_date )" +
          "group by extract(month from o.createdDate)")
  List<Object[]> countOrderByMonth();

  @Query("select sum(o.totalPrice) from Order o")
  double getTotalPrice();

  @Query("select extract(month from o.createdDate), sum(o.totalPrice) " +
          "from Order o " +
          "where extract(year from o.createdDate) = extract(year from current_date ) " +
          "group by extract(month from o.createdDate) ")
  List<Object[]> getTotalPriceByMonth();


  @Query("select extract(month from o.createdDate), count(o), sum(o.totalPrice) " +
          "from Order o " +
          "where extract(year from o.createdDate) = :year " +
          "group by extract(month from o.createdDate) ")
  Set<Object[]> getTotalRevenueByYear(int year);

  @Query("select extract(day from o.createdDate), count(o), sum(o.totalPrice) " +
          "from Order o " +
          "where extract(year from o.createdDate) = :year " +
          "and extract(month from o.createdDate) = :month " +
          "group by extract(day from o.createdDate) ")
  Set<Object[]> getTotalRevenueByMonthInYear(int year, int month);

  @Query("select extract(day from o.createdDate), count (o) " +
          "from Order o " +
          "where extract(year from o.createdDate) = :year " +
          "and extract(month from o.createdDate) = :month " +
          "group by extract(day from o.createdDate) ")
  Set<Object[]> getTotalOrderByMonthInYear(Integer year, Integer month);

  @Query("select extract(month from o.createdDate), count(o) " +
          "from Order o " +
          "where extract(year from o.createdDate) = :year " +
          "group by extract(month from o.createdDate) ")
  Set<Object[]> getTotalOrderByYear(Integer year);
}
