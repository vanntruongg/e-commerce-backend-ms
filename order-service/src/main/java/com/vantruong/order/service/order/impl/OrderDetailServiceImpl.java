package com.vantruong.order.service.order.impl;

import com.vantruong.common.dto.response.ProductResponse;
import com.vantruong.order.dto.OrderDetailRequest;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.OrderDetail;
import com.vantruong.order.repository.OrderDetailRepository;
import com.vantruong.order.repository.client.ProductClient;
import com.vantruong.order.service.order.OrderDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailServiceImpl implements OrderDetailService {
  OrderDetailRepository orderDetailRepository;
  ProductClient productClient;

  @Override
  @Transactional
  public List<OrderDetail> createOrderDetails(Order order, List<OrderDetailRequest> orderDetailDTOs) {
    List<OrderDetail> orderDetails = orderDetailDTOs.stream()
            .map(orderDetailDto -> {
              ProductResponse productResponse = productClient.getProductById(orderDetailDto.getProductId()).getData();
              return convertToOrderDetail(order, orderDetailDto, productResponse);
            })
            .toList();

    return orderDetailRepository.saveAll(orderDetails);
  }

  private OrderDetail convertToOrderDetail(Order order, OrderDetailRequest orderDetailRequest, ProductResponse productResponse) {
    return OrderDetail.builder()
            .productId(orderDetailRequest.getProductId())
            .quantity(orderDetailRequest.getQuantity())
            .productSize(orderDetailRequest.getSize())
            .productName(productResponse.getName())
            .productPrice(productResponse.getPrice())
            .productImage(productResponse.getImages().get(0).getImageUrl())
            .order(order)
            .build();
  }
}
