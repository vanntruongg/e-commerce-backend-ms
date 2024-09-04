package com.vantruong.order.service.order.impl;

import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.exception.ErrorCode;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.common.exception.ProductQuantityNotAvailableException;
import com.vantruong.order.constant.MessageConstant;
import com.vantruong.order.dto.OrderDetailRequest;
import com.vantruong.order.dto.OrderDto;
import com.vantruong.order.dto.OrderRequest;
import com.vantruong.order.entity.DeliveryAddress;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.OrderDetail;
import com.vantruong.order.entity.PaymentMethod;
import com.vantruong.order.enums.EPaymentMethod;
import com.vantruong.order.enums.OrderStatus;
import com.vantruong.order.enums.PaymentStatus;
import com.vantruong.order.repository.OrderRepository;
import com.vantruong.order.repository.client.InventoryClient;
import com.vantruong.order.repository.client.ProductClient;
import com.vantruong.order.service.order.DeliveryAddressService;
import com.vantruong.order.service.order.OrderDetailService;
import com.vantruong.order.service.order.OrderService;
import com.vantruong.order.service.payment.PaymentMethodService;
import com.vantruong.order.util.OrderConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
  OrderRepository orderRepository;
  OrderDetailService orderDetailService;
  PaymentMethodService paymentMethodService;
  DeliveryAddressService deliveryAddressService;
  ProductClient productClient;

  InventoryClient inventoryClient;
  OrderConverter orderConverter;

  @Override
  public List<OrderDto> getAllOrder() {
    List<Order> orders = orderRepository.findAll();
    return orderConverter.convertToListOrderDto(orders);
  }

  @Transactional
  @Override
  public Order createNewOrder(OrderRequest orderRequest) {
    boolean isQuantityValid = validateProductQuantities(orderRequest.getListProduct());

    if (!isQuantityValid) {
      throw new ProductQuantityNotAvailableException(ErrorCode.INSUFFICIENT_PRODUCT_QUANTITY, MessageConstant.PRODUCT_QUANTITY_NOT_AVAILABLE);
    }

    PaymentMethod paymentMethod = paymentMethodService.findById(orderRequest.getPaymentMethodId());
    OrderStatus orderStatus = paymentMethod.getSlug().equals(EPaymentMethod.COD) ? OrderStatus.PENDING_CONFIRM : OrderStatus.PENDING_PAYMENT;

    var totalPrice = calculateTotalPriceByProductId(orderRequest.getListProduct());

    Order newOrder = Order.builder()
            .email(orderRequest.getEmail())
            .totalPrice(totalPrice)
            .notes(orderRequest.getNotes())
            .orderStatus(orderStatus)
            .paymentStatus(PaymentStatus.UNPAID)
            .paymentMethod(paymentMethod)
            .build();
    Order orderSaved = orderRepository.save(newOrder);

    deliveryAddressService.createDeliveryAddress(orderRequest.getName(), orderRequest.getPhone(), orderRequest.getAddress(), orderSaved);

    List<OrderDetail> orderDetails = orderDetailService.createOrderDetails(orderSaved, orderRequest.getListProduct());
    orderSaved.setOrderDetails(orderDetails);

    return orderSaved;
  }

  private Double calculateTotalPriceByProductId(List<OrderDetailRequest> requests) {
    Map<Integer, Integer> productQuantities = new HashMap<>();

    for (OrderDetailRequest request: requests) {
      Integer productId = request.getProductId();
      Integer quantity = request.getQuantity();
      productQuantities.merge(productId, quantity, Integer::sum);
    }


    return productClient.calculateTotalPriceByProductIds(productQuantities).getData();
  }

  private boolean validateProductQuantities(List<OrderDetailRequest> listProduct) {
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

  @Override
  public void deleteOrder(Integer orderId) {
    orderRepository.deleteById(orderId);
  }

  public Order findById(int id) {
    return orderRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.ORDER_NOT_FOUND));
  }

  @Override
  public List<OrderDto> getOrderByStatus(String status) {
    try {
      OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
      List<Order> orders = orderRepository.findOrderByOrderStatus(orderStatus);
      return orderConverter.convertToListOrderDto(orders);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid order status: " + status);
    }
  }

  @Override
  public List<OrderDto> getOrderByEmailAndStatus(String email, String status) {
    try {
      OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
      List<Order> orders = orderRepository.findOrderByEmailAndOrderStatusOrderByCreatedDateDesc(email, orderStatus);
      return orderConverter.convertToListOrderDto(orders);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Invalid order status: " + status);
    }
  }

  @Override
  public List<OrderDto> getOrderByEmail(String email) {
    List<Order> orders = orderRepository.findAllByEmailOrderByCreatedDateDesc(email);
    return orderConverter.convertToListOrderDto(orders);
  }

  @Override
  public OrderDto getOrderById(int id) {
    Order order = orderRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.ORDER_NOT_FOUND));
    DeliveryAddress deliveryAddress = deliveryAddressService.getByOrder(order);
    return orderConverter.convertToOrderDto(order, orderConverter.convertToListOrderDetailDto(order.getOrderDetails()), deliveryAddress);
  }

  @Override
  @Transactional
  public Boolean updateOrderStatus(int id, OrderStatus status) {
    Order order = findById(id);
    order.setOrderStatus(status);
    orderRepository.save(order);
    return true;
  }

  @Override
  @Transactional
  public void updatePaymentStatus(int id, PaymentStatus status) {
    Order order = findById(id);
    order.setPaymentStatus(status);
    orderRepository.save(order);
  }


}
