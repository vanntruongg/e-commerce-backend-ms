package com.vantruong.order.listener;


import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.event.OrderEvent;
import com.vantruong.order.entity.enumeration.PaymentStatus;
import com.vantruong.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventListener {
  private final OrderService orderService;


  @KafkaListener(topics = KafkaTopics.PAYMENT_SUCCESS)
  public void handlePaymentSuccess(OrderEvent orderEvent) {
    orderService.updatePaymentStatus(orderEvent.getOrderId(), PaymentStatus.COMPLETED);
  }
}
