package com.vantruong.order.util;

import com.vantruong.common.dto.order.OrderItemCommonDto;
import com.vantruong.common.event.OrderEvent;
import com.vantruong.common.event.OrderEventStatus;
import com.vantruong.common.event.PaymentStatus;
import com.vantruong.common.util.DateTimeFormatter;
import com.vantruong.order.dto.OrderDto;
import com.vantruong.order.dto.OrderItemDto;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
  private final DateTimeFormatter dateTimeFormatter;

  public OrderEvent orderToKafka(Order order) {
    Set<OrderItemCommonDto> orderItemVms = order.getOrderItems().stream()
            .map(orderItem -> new OrderItemCommonDto(orderItem.getOrderItemId(),
                    orderItem.getProductId(),
                    orderItem.getQuantity(),
                    orderItem.getProductName(),
                    orderItem.getProductPrice(),
                    orderItem.getProductImage(),
                    orderItem.getProductSize()))
            .collect(Collectors.toSet());

    return new OrderEvent(order.getOrderId(),
            order.getEmail(),
            order.getNotes(),
            order.getTotalPrice(),
            order.getOrderStatus().name(),
            OrderEventStatus.NEW,
            PaymentStatus.PAYMENT_SUCCESS,
            order.getOrderAddress().getContactName(),
            order.getOrderAddress().getPhone(),
            order.getOrderAddress().getAddress(),
            orderItemVms
    );
  }

  public OrderDto convertToOrderDto(Order order, Set<com.vantruong.order.dto.OrderItemDto> orderItemDtos) {
    return OrderDto.builder()
            .orderId(order.getOrderId())
            .email(order.getEmail())
            .name(order.getOrderAddress().getContactName())
            .phone(order.getOrderAddress().getPhone())
            .address(order.getOrderAddress().getAddress())
            .notes(order.getNotes())
            .totalPrice(order.getTotalPrice())
            .orderStatus(order.getOrderStatus().name())
            .paymentStatus(order.getPaymentStatus().name())
            .paymentMethod(order.getPaymentMethod())
            .created(dateTimeFormatter.format(order.getCreatedDate()))
            .createdDate(order.getCreatedDate())
            .orderItems(orderItemDtos)
            .build();
  }

  public List<OrderDto> convertToListOrderDto(List<Order> orders) {
    return orders.stream().map(order ->
            convertToOrderDto(order, convertToListOrderDetailDto(order.getOrderItems()))

    ).toList();
  }

  public OrderItemDto convertToOrderDetailDto(OrderItem orderDetail) {
    return OrderItemDto.builder()
            .productId(orderDetail.getProductId())
            .productName(orderDetail.getProductName())
            .quantity(orderDetail.getQuantity())
            .productPrice(orderDetail.getProductPrice())
            .productImage(orderDetail.getProductImage())
            .size(orderDetail.getProductSize())
            .build();
  }

  public Set<OrderItemDto> convertToListOrderDetailDto(Set<OrderItem> orderItems) {
    return orderItems.stream().map(this::convertToOrderDetailDto).collect(Collectors.toSet());
  }
}
