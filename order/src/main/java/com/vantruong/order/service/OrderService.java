package com.vantruong.order.service;

import com.vantruong.order.constant.MessageConstant;
import com.vantruong.order.dto.OrderDto;
import com.vantruong.order.dto.OrderListDto;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.OrderAddress;
import com.vantruong.order.entity.OrderItem;
import com.vantruong.order.entity.enumeration.OrderStatus;
import com.vantruong.order.entity.enumeration.PaymentMethod;
import com.vantruong.order.entity.enumeration.PaymentStatus;
import com.vantruong.order.exception.ErrorCode;
import com.vantruong.order.exception.NotFoundException;
import com.vantruong.order.exception.ProductQuantityNotAvailableException;
import com.vantruong.order.repository.OrderItemRepository;
import com.vantruong.order.repository.OrderRepository;
import com.vantruong.order.repository.specification.OrderSpecification;
import com.vantruong.order.util.AuthenticationUtils;
import com.vantruong.order.util.OrderConverter;
import com.vantruong.order.viewmodel.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
  OrderRepository orderRepository;
  OrderItemRepository orderItemRepository;
  ProductService productService;
  InventoryService inventoryService;
  OrderConverter orderConverter;


  public OrderListDto getAllOrders(int pageNo, int pageSize, String orderStatus, String paymentMethod, String userId) {

    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate").descending());
    OrderStatus status = orderStatus == null || orderStatus.isEmpty() ? null : OrderStatus.findOrderStatus(orderStatus);

    Specification<Order> specification = Specification
            .where(OrderSpecification.hasEmail(userId))
            .and(OrderSpecification.hasOrderStatus(status))
            .and(OrderSpecification.hasPaymentMethod(paymentMethod));

    Page<Order> orderPage = orderRepository.findAll(specification, pageable);

    List<OrderDto> orders = orderConverter.convertToListOrderDto(orderPage.getContent());
    return new OrderListDto(
            orders,
            (int) orderPage.getTotalElements(),
            orderPage.getTotalPages(),
            orderPage.isLast()
    );
  }

  public OrderListDto searchById(Long orderId) {
    Order order = findById(orderId);
    return new OrderListDto(
            List.of(orderConverter.convertToOrderDto(order)),
            1,
            1,
            true
    );
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
  public OrderListDto getAllOrderByAdmin(int pageNo, int pageSize, String orderStatus, String paymentMethod) {
    return getAllOrders(pageNo, pageSize, orderStatus, paymentMethod, null);
  }

  public OrderListDto getAllMyOrder(int pageNo, int pageSize, String orderStatus) {
    String userId = AuthenticationUtils.extractUserId();
    return getAllOrders(pageNo, pageSize, orderStatus, null, userId);
  }

  @Transactional
  public Order createNewOrder(OrderPostVm orderRequest) {
    boolean isQuantityValid = validateProductQuantities(orderRequest.getOrderItemVms());

    if (!isQuantityValid) {
      throw new ProductQuantityNotAvailableException(
              ErrorCode.INSUFFICIENT_PRODUCT_QUANTITY,
              MessageConstant.PRODUCT_QUANTITY_NOT_AVAILABLE
      );
    }

    Double totalPrice = calculateTotalOrderPrice(orderRequest.getOrderItemVms());
    OrderAddress orderAddress = mapToOrderAddress(orderRequest);
    String userId = AuthenticationUtils.extractUserId();
    LocalDateTime paymentStartTime = null;

    if (orderRequest.getPaymentMethod() == PaymentMethod.VN_PAY) {
      paymentStartTime = LocalDateTime.now();
    }

    Order order = Order.builder()
            .email(userId)
            .totalPrice(totalPrice)
            .notes(orderRequest.getNotes())
            .orderStatus(OrderStatus.PENDING)
            .paymentStatus(PaymentStatus.PENDING)
            .paymentMethod(orderRequest.getPaymentMethod())
            .orderAddress(orderAddress)
            .paymentStartTime(paymentStartTime)
            .build();
    orderRepository.save(order);

    Set<OrderItem> orderItems = createOrderItems(orderRequest.getOrderItemVms(), order);
    // setOrderItems so that able to return order with orderItems
    order.setOrderItems(orderItems);

    return order;
  }

  private Set<OrderItem> createOrderItems(List<OrderItemVm> orderItemVms, Order order) {
    List<OrderItem> orderItems = orderItemVms.stream()
            .map(item -> OrderItem.builder()
                    .productId(item.productId())
                    .quantity(item.quantity())
                    .productSize(item.size())
                    .productName(item.productName())
                    .productPrice(item.productPrice())
                    .productImage(item.productImage())
                    .order(order)
                    .build())
            .toList();
    return new HashSet<>(orderItemRepository.saveAll(orderItems));
  }

  private OrderAddress mapToOrderAddress(OrderPostVm orderRequest) {
    return OrderAddress.builder()
            .contactName(orderRequest.getName())
            .phone(orderRequest.getPhone())
            .address(orderRequest.getAddress())
            .build();
  }

  private Double calculateTotalOrderPrice(List<OrderItemVm> orderItemVms) {
    List<ProductQuantityVm> productQuantityVms = orderItemVms.stream()
            .map(orderItemVm -> new ProductQuantityVm(orderItemVm.productId(), orderItemVm.quantity()))
            .collect(Collectors.toList());

    return productService.calculateTotalOrderPrice(new CalculateTotalOrderPricePostVm(productQuantityVms)).totalPrice();
  }

  private boolean validateProductQuantities(List<OrderItemVm> orderItemVms) {
    List<ProductQuantityCheckVm> request = orderItemVms.stream()
            .map(orderItem ->
                    new ProductQuantityCheckVm(
                            orderItem.productId(),
                            orderItem.size(),
                            orderItem.quantity())
            )
            .toList();
    return inventoryService.checkListProductQuantity(request);
  }

  public void deleteOrder(Long orderId) {
    orderRepository.deleteById(orderId);
  }

  public Order findById(Long id) {
    return orderRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.ORDER_NOT_FOUND));
  }

  public List<OrderDto> getOrderByStatus(String status) {
    try {
      OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
      List<Order> orders = orderRepository.findOrderByOrderStatus(orderStatus);
      return orderConverter.convertToListOrderDto(orders);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid order status: " + status);
    }
  }

  public OrderDto getOrderById(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.ORDER_NOT_FOUND));
    return orderConverter.convertToOrderDto(order);
  }

  @Transactional
  public Boolean updateOrderStatus(Long id, OrderStatus status) {
    Order order = findById(id);
    order.setOrderStatus(status);
    orderRepository.save(order);
    return true;
  }

  public OrderCheckResultVm isOrderCompleted(String email, Long productId) {
    boolean result = orderRepository.isOrderCompleted(email, productId);
    return new OrderCheckResultVm(result);
  }

  public List<Order> findOrdersByStatusAndTime(PaymentStatus status, LocalDateTime time) {
    return orderRepository.findByPaymentStatusAndPaymentStartTimeBefore(status, time);
  }


}
