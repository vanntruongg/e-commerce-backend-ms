package com.vantruong.notification.listener;

import com.vantruong.notification.service.MailService;
import com.vantruong.notification.viewmodel.OrderEventVm;
import com.vantruong.notification.viewmodel.OrderVm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderListener {
  private final MailService mailService;

  private static final String NOTIFICATION_TOPIC = "notification-topic";

  @KafkaListener(topics = NOTIFICATION_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
  public void processSendMailRequest(OrderEventVm orderEvent) {
    try {
      OrderVm orderVm = new OrderVm(
              orderEvent.orderId(),
              orderEvent.email(),
              orderEvent.name(),
              orderEvent.phone(),
              orderEvent.address(),
              orderEvent.notes(),
              orderEvent.totalPrice(),
              orderEvent.orderStatus(),
              orderEvent.paymentStatus(),
              orderEvent.orderItems()
      );
      mailService.confirmOrder(orderVm);
      log.info("Email xác nhận đơn hàng đã được gửi cho đơn hàng {}", orderEvent.orderId());
    } catch (Exception e) {
      log.error("Lỗi khi gửi email xác nhận đơn hàng cho đơn hàng {}: {}", orderEvent.orderId(), e.getMessage());
    }
  }

}
