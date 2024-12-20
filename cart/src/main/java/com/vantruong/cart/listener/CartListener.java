package com.vantruong.cart.listener;

import com.vantruong.cart.service.CartService;
import com.vantruong.cart.viewmodel.CartItemDeleteVm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CartListener {
  private final CartService cartService;

  private static final String CART_TOPIC = "cart-topic";

  @KafkaListener(topics = CART_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
  public void processCartRequest(CartItemDeleteVm cartItemDeleteVm) {
    try {
      cartService.removeItemsFromCart(cartItemDeleteVm);
    } catch (Exception e) {
      log.error("Handle clear cart exception: {}", e.getMessage());
    }
  }


}
