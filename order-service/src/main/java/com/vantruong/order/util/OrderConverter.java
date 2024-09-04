package com.vantruong.order.util;

import com.vantruong.common.dto.UserAddress;
import com.vantruong.common.entity.OrderDetail;
import com.vantruong.common.event.OrderEvent;
import com.vantruong.common.event.OrderEventStatus;
import com.vantruong.common.event.PaymentStatus;
import com.vantruong.common.util.AddressFormatter;
import com.vantruong.common.util.DateTimeFormatter;
import com.vantruong.order.dto.OrderDetailDto;
import com.vantruong.order.dto.OrderDto;
import com.vantruong.order.dto.OrderSendMailRequest;
import com.vantruong.order.entity.DeliveryAddress;
import com.vantruong.order.entity.Order;
import com.vantruong.order.service.order.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderConverter {
  private final DateTimeFormatter dateTimeFormatter;
  private final AddressFormatter addressFormatter;
  private final DeliveryAddressService deliveryAddressService;

  public OrderEvent orderToKafka(Order order, DeliveryAddress deliveryAddress) {
    List<OrderDetail> orderDetails = order.getOrderDetails().stream()
            .map(orderDetail -> OrderDetail.builder()
                    .orderDetailId(orderDetail.getOrderDetailId())
                    .productId(orderDetail.getProductId())
                    .quantity(orderDetail.getQuantity())
                    .productName(orderDetail.getProductName())
                    .productPrice(orderDetail.getProductPrice())
                    .productImage(orderDetail.getProductImage())
                    .productSize(orderDetail.getProductSize())
                    .build()).toList();

    return OrderEvent.builder()
            .orderId(order.getOrderId())
            .email(order.getEmail())
            .name(deliveryAddress.getName())
            .phone(deliveryAddress.getPhone())
            .address(deliveryAddress.getAddress())
            .totalPrice(order.getTotalPrice())
            .orderStatus(OrderEventStatus.NEW)
            .paymentStatus(PaymentStatus.PAYMENT_SUCCESS)
            .orderDetails(orderDetails)
            .notes(order.getNotes())
            .build();
  }

//  public List<OrderEvent> listOrderToKafka(List<Order> orders) {
//    return orders.stream().map(this::orderToKafka).toList();
//  }

  public OrderDto convertToOrderDto(Order order, List<OrderDetailDto> orderDetail, DeliveryAddress deliveryAddress) {
    return OrderDto.builder()
            .orderId(order.getOrderId())
            .email(order.getEmail())
            .name(deliveryAddress.getName())
            .phone(deliveryAddress.getPhone())
            .address(deliveryAddress.getAddress())
            .notes(order.getNotes())
            .totalPrice(order.getTotalPrice())
            .orderStatus(order.getOrderStatus().name())
            .paymentStatus(order.getPaymentStatus().name())
            .paymentMethod(order.getPaymentMethod().getMethod())
            .created(dateTimeFormatter.format(order.getCreatedDate()))
            .createdDate(order.getCreatedDate())
            .orderDetail(orderDetail)
            .build();
  }


  public OrderSendMailRequest convertToOrderDtoSendMailRequest(Order order, List<com.vantruong.order.entity.OrderDetail> orderDetail, UserAddress userAddress) {
    return OrderSendMailRequest.builder()
            .orderId(order.getOrderId())
            .email(order.getEmail())
            .notes(order.getNotes())
            .name(userAddress.getName())
            .phone(userAddress.getPhone())
            .address(addressFormatter.formatAddress(userAddress))
            .totalPrice(order.getTotalPrice())
            .orderStatus(order.getOrderStatus().getOrderStatus())
            .createdDate(order.getCreatedDate())
            .orderDetail(orderDetail)
            .build();
  }

  public List<OrderDto> convertToListOrderDto(List<Order> orders) {
    return orders.stream().map(order -> {
            DeliveryAddress deliveryAddress = deliveryAddressService.getByOrder(order);
            return  convertToOrderDto(order, convertToListOrderDetailDto(order.getOrderDetails()), deliveryAddress);
            }
    ).toList();
  }

  public OrderDetailDto convertToOrderDetailDto(com.vantruong.order.entity.OrderDetail orderDetail) {
    return OrderDetailDto.builder()
            .productId(orderDetail.getProductId())
            .productId(orderDetail.getProductId())
            .productName(orderDetail.getProductName())
            .quantity(orderDetail.getQuantity())
            .productPrice(orderDetail.getProductPrice())
            .productImage(orderDetail.getProductImage())
            .size(orderDetail.getProductSize())
            .build();
  }

  public List<OrderDetailDto> convertToListOrderDetailDto(List<com.vantruong.order.entity.OrderDetail> orderDetails) {
    return orderDetails.stream().map(this::convertToOrderDetailDto).toList();
  }
}
