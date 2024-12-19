package com.vantruong.order.service;

import com.vantruong.common.event.CancelOrderEvent;
import com.vantruong.common.event.CancelOrderItem;
import com.vantruong.order.entity.Order;
import com.vantruong.order.entity.enumeration.PaymentStatus;
import com.vantruong.order.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentTimeOutChecker {

  private final OrderService orderService;
  private final KafkaProducer kafkaProducer;

  @Scheduled(fixedRate = 300000)
  public void checkPendingPayments() {
    LocalDateTime timeoutThreshold = LocalDateTime.now().minusMinutes(5);

    List<Order> orders = orderService.findOrdersByStatusAndTime(PaymentStatus.PENDING, timeoutThreshold);

    for (Order order : orders) {
      CancelOrderEvent event = createCancelOrderEvent(order);
      kafkaProducer.sendCancelOrder(event);

      orderService.deleteOrder(order.getOrderId());
    }
  }

  private CancelOrderEvent createCancelOrderEvent(Order order) {
    Set<CancelOrderItem> cancelOrderItems = order.getOrderItems().stream()
            .map(item -> new CancelOrderItem(
                    item.getProductId(),
                    item.getQuantity(),
                    item.getProductSize()
            ))
            .collect(Collectors.toSet());
    return CancelOrderEvent.builder()
            .orderItems(cancelOrderItems)
            .build();
  }
}
