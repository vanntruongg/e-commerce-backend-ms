package com.vantruong.order.service;

import com.vantruong.common.dto.order.OrderExistsByProductAndUser;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.exception.Constant;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.common.exception.ProductQuantityNotAvailableException;
import com.vantruong.order.constant.MessageConstant;
import com.vantruong.order.dto.OrderDto;
import com.vantruong.order.dto.OrderItemRequest;
import com.vantruong.order.dto.OrderListDto;
import com.vantruong.order.dto.OrderRequest;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.OrderAddress;
import com.vantruong.order.entity.OrderItem;
import com.vantruong.order.entity.enumeration.OrderStatus;
import com.vantruong.order.entity.enumeration.PaymentMethod;
import com.vantruong.order.entity.enumeration.PaymentStatus;
import com.vantruong.order.repository.OrderItemRepository;
import com.vantruong.order.repository.OrderRepository;
import com.vantruong.order.repository.specification.OrderSpecification;
import com.vantruong.order.util.AuthenticationUtils;
import com.vantruong.order.util.OrderConverter;
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
import java.util.*;

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

  public void updatePaymentStatus(Long orderId, PaymentStatus paymentStatus) {
    Order order = findById(orderId);
    order.setPaymentStatus(paymentStatus);
    orderRepository.save(order);
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
  public Order createNewOrder(OrderRequest orderRequest) {
    boolean isQuantityValid = validateProductQuantities(orderRequest.getOrderItemRequests());

    if (!isQuantityValid) {
      throw new ProductQuantityNotAvailableException(
              Constant.ErrorCode.INSUFFICIENT_PRODUCT_QUANTITY,
              MessageConstant.PRODUCT_QUANTITY_NOT_AVAILABLE
      );
    }

    Double totalPrice = calculateTotalOrderPrice(orderRequest.getOrderItemRequests());
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

    Set<OrderItem> orderItems = createOrderItems(orderRequest.getOrderItemRequests(), order);
    // setOrderItems so that able to return order with orderItems
    order.setOrderItems(orderItems);

    return order;
  }

  private Set<OrderItem> createOrderItems(List<OrderItemRequest> requests, Order order) {
    List<OrderItem> orderItems = requests.stream()
            .map(item -> OrderItem.builder()
                    .productId(item.getProductId())
                    .quantity(item.getQuantity())
                    .productSize(item.getSize())
                    .productName(item.getProductName())
                    .productPrice(item.getProductPrice())
                    .productImage(item.getProductImage())
                    .order(order)
                    .build())
            .toList();
    return new HashSet<>(orderItemRepository.saveAll(orderItems));
  }

  private OrderAddress mapToOrderAddress(OrderRequest orderRequest) {
    return OrderAddress.builder()
            .contactName(orderRequest.getName())
            .phone(orderRequest.getPhone())
            .address(orderRequest.getAddress())
            .build();
  }

  private Double calculateTotalOrderPrice(List<OrderItemRequest> requests) {
    Map<Long, Integer> productQuantities = new HashMap<>();

    for (OrderItemRequest request : requests) {
      Long productId = request.getProductId();
      Integer quantity = request.getQuantity();
      productQuantities.merge(productId, quantity, Integer::sum);
    }


    return productService.calculateTotalOrderPrice(productQuantities);
  }

  private boolean validateProductQuantities(List<OrderItemRequest> listProduct) {
    List<ProductQuantityRequest> request = listProduct.stream()
            .map(orderDetailDto ->
                    ProductQuantityRequest.builder()
                            .productId(orderDetailDto.getProductId())
                            .size(orderDetailDto.getSize())
                            .quantity(orderDetailDto.getQuantity())
                            .build()
            )
            .toList();
    return inventoryService.checkListProductQuantity(request);
  }

  public void deleteOrder(Long orderId) {
    orderRepository.deleteById(orderId);
  }

  public Order findById(Long id) {
    return orderRepository.findById(id).orElseThrow(() ->
            new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.ORDER_NOT_FOUND));
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
            new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.ORDER_NOT_FOUND));
    return orderConverter.convertToOrderDto(order);
  }

  @Transactional
  public Boolean updateOrderStatus(Long id, OrderStatus status) {
    Order order = findById(id);
    order.setOrderStatus(status);
    orderRepository.save(order);
    return true;
  }

  public OrderExistsByProductAndUser checkOrderExistsByProductAndUserWithStatus(String email, Long productId) {
    return new OrderExistsByProductAndUser(
            orderRepository.existsByEmailAndProductIdAndOrderStatus(email, productId, OrderStatus.COMPLETED)
    );
  }

  public List<Order> findOrdersByStatusAndTime(PaymentStatus status, LocalDateTime time) {
    return orderRepository.findByPaymentStatusAndPaymentStartTimeBefore(status, time);
  }


}
