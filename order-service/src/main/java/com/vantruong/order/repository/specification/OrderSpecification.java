package com.vantruong.order.repository.specification;

import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.enumeration.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {
  public static Specification<Order> hasEmail(String email) {
    return (root, query, criteriaBuilder) ->
            email == null
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.equal(root.get("email"), email);
  }

  public static Specification<Order> hasOrderStatus(OrderStatus orderStatus) {
    return (root, query, criteriaBuilder) ->
            orderStatus == null
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.equal(root.get("orderStatus"), orderStatus);
  }

  public static Specification<Order> hasPaymentMethod(String paymentMethod) {
    return (root, query, criteriaBuilder) ->
            paymentMethod == null || paymentMethod.isEmpty()
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.equal(root.get("paymentMethod"), paymentMethod);
  }
}
