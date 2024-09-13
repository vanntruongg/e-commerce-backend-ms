package com.vantruong.cart.service;

import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.dto.cart.CartItemCommon;
import com.vantruong.common.dto.request.DeleteCartItemsRequest;
import com.vantruong.common.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderManagementService {
  private final CartService cartService;

  @KafkaListener(topics = KafkaTopics.CART_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
  public void processCartRequest(OrderEvent orderEvent) {
    try {
      List<CartItemCommon> cartItems = orderEvent.getOrderItems().stream()
              .map(orderItemDto -> CartItemCommon.builder()
                      .productId(orderItemDto.productId())
                      .size(orderItemDto.productSize())
                      .build()).toList();
      DeleteCartItemsRequest deleteCartItemsRequest = DeleteCartItemsRequest.builder()
              .email(orderEvent.getEmail())
              .items(cartItems)
              .build();
      cartService.removeItemsFromCart(deleteCartItemsRequest);
      log.info("Clear cart successfully!");
    } catch (Exception e) {
      log.error("Handle clear cart exception: {}", e.getMessage());
    }
  }


}
