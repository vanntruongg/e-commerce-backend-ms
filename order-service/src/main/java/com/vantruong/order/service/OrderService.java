package com.vantruong.order.service;

import com.vantruong.common.dto.order.OrderExistsByProductAndUser;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.exception.Constant;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.common.exception.ProductQuantityNotAvailableException;
import com.vantruong.order.client.InventoryClient;
import com.vantruong.order.client.ProductClient;
import com.vantruong.order.constant.MessageConstant;
import com.vantruong.order.dto.OrderDto;
import com.vantruong.order.dto.OrderItemRequest;
import com.vantruong.order.dto.OrderRequest;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.OrderAddress;
import com.vantruong.order.entity.OrderItem;
import com.vantruong.order.entity.enumeration.OrderStatus;
import com.vantruong.order.entity.enumeration.PaymentMethod;
import com.vantruong.order.entity.enumeration.PaymentStatus;
import com.vantruong.order.repository.OrderItemRepository;
import com.vantruong.order.repository.OrderRepository;
import com.vantruong.order.util.AuthenticationUtils;
import com.vantruong.order.util.OrderConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
  OrderRepository orderRepository;
  OrderItemRepository orderItemRepository;
  ProductClient productClient;
  InventoryClient inventoryClient;
  OrderConverter orderConverter;

  public List<OrderDto> getAllOrder() {
    List<Order> orders = orderRepository.findAll();
    return orderConverter.convertToListOrderDto(orders);
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
    PaymentStatus paymentStatus = orderRequest.getPaymentMethod().equals(PaymentMethod.COD) ? PaymentStatus.PENDING : PaymentStatus.COMPLETED;
    String userId = AuthenticationUtils.extractUserId();
    Order order = Order.builder()
            .email(userId)
            .totalPrice(totalPrice)
            .notes(orderRequest.getNotes())
            .orderStatus(OrderStatus.PENDING)
            .paymentStatus(paymentStatus)
            .paymentMethod(orderRequest.getPaymentMethod())
            .orderAddress(orderAddress)
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


    return productClient.calculateTotalOrderPrice(productQuantities).getData();
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
    var response = inventoryClient.checkListProductQuantity(request);
    return response.getData();
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

//  public List<OrderDto> getOrderOfUserByStatus(String status) {
//    try {
//      String email = authService.getUserId();;
//      OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
//      List<Order> orders = orderRepository.findOrderByEmailAndOrderStatusOrderByCreatedDateDesc(email, orderStatus);
//      return orderConverter.convertToListOrderDto(orders);
//    } catch (IllegalArgumentException e) {
//      throw new IllegalArgumentException("Invalid order status: " + status);
//    }
//  }

  public List<OrderDto> getOrderByUser() {
    String userId = AuthenticationUtils.extractUserId();
    List<Order> orders = orderRepository.findAllByEmailOrderByCreatedDateDesc(userId);
    return orderConverter.convertToListOrderDto(orders);
  }

  public OrderDto getOrderById(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(() ->
            new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.ORDER_NOT_FOUND));
    return orderConverter.convertToOrderDto(order, orderConverter.convertToListOrderDetailDto(order.getOrderItems()));
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
}
