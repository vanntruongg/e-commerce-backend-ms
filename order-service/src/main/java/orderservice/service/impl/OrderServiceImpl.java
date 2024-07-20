package orderservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import orderservice.constant.MessageConstant;
import orderservice.dto.UserAddress;
import orderservice.entity.Order;
import orderservice.entity.OrderDetail;
import orderservice.entity.PaymentMethod;
import orderservice.dto.OrderDto;
import orderservice.dto.OrderRequest;
import orderservice.enums.OrderStatus;
import orderservice.exception.ErrorCode;
import orderservice.exception.NotFoundException;
import orderservice.repository.OrderRepository;
import orderservice.repository.client.MailClient;
import orderservice.repository.client.UserAddressClient;
import orderservice.service.OrderDetailService;
import orderservice.service.OrderService;
import orderservice.service.PaymentMethodService;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
  OrderRepository orderRepository;
  OrderDetailService orderDetailService;
  PaymentMethodService paymentMethodService;
  MailClient mailClient;
  UserAddressClient userAddressClient;


  @Override
  public List<OrderDto> getAllOrder() {
    List<Order> orders = orderRepository.findAll();
    return convertToListOrderDto(orders);
  }

  private Order findById(int id) {
    return orderRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.ORDER_NOT_FOUND));
  }

  @Override
  @Transactional
  public OrderRequest createOrder(OrderRequest orderRequest) {
    PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(orderRequest.getPaymentMethodId());

    Order newOrder = Order.builder()
            .email(orderRequest.getEmail())
            .totalPrice(orderRequest.getTotalPrice())
            .notes(orderRequest.getNotes())
            .addressId(orderRequest.getAddressId())
            .orderStatus(OrderStatus.PENDING_CONFIRM)
            .paymentMethod(paymentMethod)
            .build();
    Order savedOrder = orderRepository.save(newOrder);
    List<OrderDetail> orderDetails = orderDetailService.createOrderDetails(newOrder, orderRequest.getListProduct());
    UserAddress userAddress = userAddressClient.getUserAddressById(savedOrder.getAddressId()).getData();
    mailClient.sendMailConfirmOrder(convertToOrderDto(savedOrder, orderDetails, userAddress));
    return orderRequest;
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
    Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, "Không tìm thấy đơn hàng có mã: " + id));
    List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetailByOrder(order);
    UserAddress userAddress = userAddressClient.getUserAddressById(order.getAddressId()).getData();
    return convertToOrderDto(order, orderDetails, userAddress);
  }

  @Override
  @Transactional
  public Boolean updateStatus(int id, String status) {
    Order order = findById(id);
    order.setOrderStatus(OrderStatus.findOrderStatus(status));
    orderRepository.save(order);
    return true;
  }
  private OrderDto convertToOrderDto(Order order, List<OrderDetail> orderDetail, UserAddress userAddress) {
    return OrderDto.builder()
            .orderId(order.getOrderId())
            .email(order.getEmail())
            .notes(order.getNotes())
            .address(userAddress)
            .totalPrice(order.getTotalPrice())
            .orderStatus(order.getOrderStatus().getName())
            .paymentMethod(order.getPaymentMethod().getMethod())
            .createdDate(order.getCreatedDate())
            .orderDetail(orderDetail)
            .build();
  }

  private List<OrderDto> convertToListOrderDto(List<Order> orders) {
    return orders.stream().map(order -> {
      List<OrderDetail> orderDetail = orderDetailService.getAllOrderDetailByOrder(order);
      UserAddress userAddress = userAddressClient.getUserAddressById(order.getAddressId()).getData();
      return convertToOrderDto(order, orderDetail, userAddress);
    }).toList();
  }
}
