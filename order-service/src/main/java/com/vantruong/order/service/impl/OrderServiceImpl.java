package com.vantruong.order.service.impl;

import com.vantruong.order.constant.MessageConstant;
import com.vantruong.order.dto.*;
import com.vantruong.order.entity.OrderDetail;
import com.vantruong.order.exception.ErrorCode;
import com.vantruong.order.exception.NotFoundException;
import com.vantruong.order.repository.OrderRepository;
import com.vantruong.order.repository.client.MailClient;
import com.vantruong.order.repository.client.UserAddressClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import orderservice.dto.*;
import com.vantruong.order.entity.Order;
import com.vantruong.order.enums.OrderStatus;
import com.vantruong.order.repository.client.PaymentClient;
import com.vantruong.order.service.OrderDetailService;
import com.vantruong.order.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
  OrderRepository orderRepository;
  OrderDetailService orderDetailService;
  MailClient mailClient;
  UserAddressClient userAddressClient;
  PaymentClient paymentClient;

  @Override
  public List<OrderDto> getAllOrder() {
    List<Order> orders = orderRepository.findAll();
    return convertToListOrderDto(orders);
  }

  @Override
  @Transactional
  public OrderRequest placeOrder(OrderRequest orderRequest) {
    Order newOrder = createNewOrder(orderRequest);
    List<OrderDetail> orderDetails = orderDetailService.createOrderDetails(newOrder, orderRequest.getListProduct());
//    call address service get delivery address
    UserAddress userAddress = userAddressClient.getUserAddressById(newOrder.getAddressId()).getData();
//    call payment service
    processPayment(newOrder.getOrderId(), orderRequest.getPaymentMethodId(), newOrder.getTotalPrice());
    mailClient.sendMailConfirmOrder(convertToOrderDtoSendMailRequest(newOrder, orderDetails, userAddress));
    return orderRequest;
  }

  @Transactional
  public Order createNewOrder(OrderRequest orderRequest) {
    Order newOrder = Order.builder()
            .email(orderRequest.getEmail())
            .totalPrice(orderRequest.getTotalPrice())
            .notes(orderRequest.getNotes())
            .addressId(orderRequest.getAddressId())
            .build();
    return orderRepository.save(newOrder);
  }

  private void processPayment(int orderId, int paymentMethodId, double amount) {
    PaymentRequest paymentRequest = PaymentRequest.builder()
            .orderId(orderId)
            .methodId(paymentMethodId)
            .amount(amount)
            .build();
    paymentClient.createPayment(paymentRequest);
  }

  private Order findById(int id) {
    return orderRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.ORDER_NOT_FOUND));
  }

  @Override
  public List<OrderDto> getOrderByStatus(String status) {
    try {
      OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
      List<Order> orders = orderRepository.findOrderByOrderStatus(orderStatus);
      return convertToListOrderDto(orders);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid order status: " + status);
    }
  }

  @Override
  public List<OrderDto> getOrderByEmailAndStatus(String email, String status) {
    try {
      OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
      List<Order> orders = orderRepository.findOrderByEmailAndOrderStatusOrderByCreatedDateDesc(email, orderStatus);
      return convertToListOrderDto(orders);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid order status: " + status);
    }
  }

  @Override
  public List<OrderDto> getOrderByEmail(String email) {
    List<Order> orders = orderRepository.findAllByEmailOrderByCreatedDateDesc(email);
    return convertToListOrderDto(orders);
  }

  @Override
  public OrderDto getOrderById(int id) {
    Order order = orderRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.ORDER_NOT_FOUND));

    List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetailByOrder(order);
    return convertToOrderDto(order, orderDetails);
  }

  @Override
  @Transactional
  public Boolean updateStatus(int id, String status) {
    Order order = findById(id);
    order.setOrderStatus(OrderStatus.findOrderStatus(status));
    orderRepository.save(order);
    return true;
  }

  private OrderDto convertToOrderDto(Order order, List<OrderDetail> orderDetail) {
    return OrderDto.builder()
            .orderId(order.getOrderId())
            .email(order.getEmail())
            .notes(order.getNotes())
            .addressId(order.getAddressId())
            .totalPrice(order.getTotalPrice())
            .orderStatus(order.getOrderStatus().getName())
            .createdDate(order.getCreatedDate())
            .orderDetail(orderDetail)
            .build();
  }

  private String formatAddress(UserAddress userAddress) {
    return userAddress.getStreet() + ", " + userAddress.getWard() + ", " + userAddress.getDistrict() + ", " + userAddress.getProvince();
  }

  private OrderSendMailRequest convertToOrderDtoSendMailRequest(Order order, List<OrderDetail> orderDetail, UserAddress userAddress) {
    return OrderSendMailRequest.builder()
            .orderId(order.getOrderId())
            .email(order.getEmail())
            .notes(order.getNotes())
            .name(userAddress.getName())
            .phone(userAddress.getPhone())
            .address(formatAddress(userAddress))
            .totalPrice(order.getTotalPrice())
            .orderStatus(order.getOrderStatus().getName())
            .createdDate(order.getCreatedDate())
            .orderDetail(orderDetail)
            .build();
  }

  private List<OrderDto> convertToListOrderDto(List<Order> orders) {
    return orders.stream().map(order -> {
      List<OrderDetail> orderDetail = orderDetailService.getAllOrderDetailByOrder(order);
      return convertToOrderDto(order, orderDetail);
    }).toList();
  }
}
