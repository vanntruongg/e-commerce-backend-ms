package com.vantruong.order.service.order.impl;

import com.vantruong.order.dto.OrderDetailDto;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.OrderDetail;
import com.vantruong.order.repository.OrderDetailRepository;
import com.vantruong.order.repository.client.CartClient;
import com.vantruong.order.service.order.OrderDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailServiceImpl implements OrderDetailService {
  OrderDetailRepository orderDetailRepository;
  CartClient cartClient;

  @Override
  @Transactional
  public List<OrderDetail> createOrderDetails(Order order, List<OrderDetailDto> orderDetailDTOs) {
    List<OrderDetail> orderDetails = orderDetailDTOs.stream()
            .map(orderDetailDto -> convertToOrderDetail(order, orderDetailDto))
            .toList();

    return orderDetailRepository.saveAll(orderDetails);
  }
//  call the cart service to remove items from the cart
//    List<CartItem> cartItems = orderDetailDTOs.stream()
//            .map(orderDetailDto -> CartItem.builder()
//                    .productId(orderDetailDto.getProductId())
//                    .size(orderDetailDto.getProductSize())
//                    .build())
//            .toList();
//
//    DeleteCartItemsRequest removeItemsCartRequest = DeleteCartItemsRequest.builder()
//            .email(order.getEmail())
//            .items(cartItems)
//            .build();
//    cartClient.removeItemsFromCart(removeItemsCartRequest);

//  @Override
//  public List<OrderDetailDto> getAllOrderDetailByOrder(Order order) {
//    return convertToListOrderDetailDto(orderDetailRepository.findAllByOrder(order));
//  }

  private OrderDetail convertToOrderDetail(Order order, OrderDetailDto orderDetailDto) {
    return OrderDetail.builder()
            .productId(orderDetailDto.getProductId())
            .productName(orderDetailDto.getProductName())
            .quantity(orderDetailDto.getQuantity())
            .productPrice(orderDetailDto.getProductPrice())
            .productImage(orderDetailDto.getProductImage())
            .productSize(orderDetailDto.getProductSize())
            .order(order)
            .build();
  }
//
//  private OrderDetailDto convertToOrderDetailDto(OrderDetail orderDetail) {
//    return OrderDetailDto.builder()
//            .productId(orderDetail.getProductId())
//            .productId(orderDetail.getProductId())
//            .productName(orderDetail.getProductName())
//            .quantity(orderDetail.getQuantity())
//            .productPrice(orderDetail.getProductPrice())
//            .productImage(orderDetail.getProductImage())
//            .productSize(orderDetail.getProductSize())
//            .build();
//  }

//  private List<OrderDetailDto> convertToListOrderDetailDto(List<OrderDetail> orderDetails) {
//    List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
//    orderDetails.forEach(orderDetail -> orderDetailDtos.add(convertToOrderDetailDto(orderDetail)));
//    return orderDetailDtos;
//  }
}
