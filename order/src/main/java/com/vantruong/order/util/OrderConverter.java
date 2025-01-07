package com.vantruong.order.util;

import com.vantruong.order.dto.OrderDto;
import com.vantruong.order.dto.OrderItemDto;
import com.vantruong.order.dto.SizeQuantity;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
  private final DateTimeFormatter dateTimeFormatter;

//  public OrderEvent orderToKafka(Order order) {
//    Set<OrderItemCommonDto> orderItemVms = order.getOrderItems().stream()
//            .map(orderItem -> new OrderItemCommonDto(orderItem.getOrderItemId(),
//                    orderItem.getProductId(),
//                    orderItem.getQuantity(),
//                    orderItem.getProductName(),
//                    orderItem.getProductPrice(),
//                    orderItem.getProductImage(),
//                    orderItem.getProductSize()))
//            .collect(Collectors.toSet());
//
//    return new OrderEvent(order.getOrderId(),
//            order.getEmail(),
//            order.getNotes(),
//            order.getTotalPrice(),
//            order.getOrderStatus().name(),
//            OrderEventStatus.NEW,
//            PaymentStatus.PAYMENT_SUCCESS,
//            order.getOrderAddress().getContactName(),
//            order.getOrderAddress().getPhone(),
//            order.getOrderAddress().getAddress(),
//            orderItemVms
//    );
//  }

  public OrderDto convertToOrderDto(Order order) {
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
            .orderItems(convertToListOrderItemDto(order.getOrderItems()))
            .build();
  }

  public List<OrderDto> convertToListOrderDto(List<Order> orders) {
    return orders.stream().map(this::convertToOrderDto).toList();
  }

  private Set<OrderItemDto> convertToListOrderItemDto(Set<OrderItem> orderItems) {
    Map<Long, List<OrderItem>> groupedByProductId = orderItems.stream()
            .collect(Collectors.groupingBy(OrderItem::getProductId));

    return groupedByProductId.entrySet().stream().map(entry -> {
      Long productId = entry.getKey();
      List<OrderItem> items = entry.getValue();

      String productName = items.get(0).getProductName();
      Double productPrice = items.get(0).getProductPrice();
      String productImage = items.get(0).getProductImage();

      List<SizeQuantity> sizeQuantities = items.stream()
              .map(item -> new SizeQuantity(item.getProductSize(), item.getQuantity()))
              .toList();

      return new OrderItemDto(
              productId,
              productName,
              productPrice,
              productImage,
              sizeQuantities
      );
    }).collect(Collectors.toSet());
  }

  public OrderItemDto convertToOrderDetailDto(OrderItem orderDetail) {
    List<SizeQuantity> sizeQuantityList = new ArrayList<>();
    SizeQuantity sizeQuantity = SizeQuantity.builder()
            .size(orderDetail.getProductSize())
            .quantity(orderDetail.getQuantity())
            .build();
    sizeQuantityList.add(sizeQuantity);

    return OrderItemDto.builder()
            .productId(orderDetail.getProductId())
            .productName(orderDetail.getProductName())
            .productPrice(orderDetail.getProductPrice())
            .productImage(orderDetail.getProductImage())
            .sizeQuantityList(sizeQuantityList)
            .build();
  }

  public Set<OrderItemDto> convertToListOrderDetailDto(Set<OrderItem> orderItems) {
    return orderItems.stream().map(this::convertToOrderDetailDto).collect(Collectors.toSet());
  }
}
