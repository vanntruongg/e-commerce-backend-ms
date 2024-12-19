package com.vantruong.notification.service;

import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.event.OrderEvent;
import com.vantruong.common.dto.order.OrderCommonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderManagementService {
  private final MailService mailService;

  @KafkaListener(topics = KafkaTopics.NOTIFICATION_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
  public void processSendMailRequest(OrderEvent orderEvent) {
    try {
      OrderCommonDto orderVm = new OrderCommonDto(orderEvent.getOrderId(),
              orderEvent.getEmail(),
              orderEvent.getName(),
              orderEvent.getPhone(),
              orderEvent.getAddress(),
              orderEvent.getNotes(),
              orderEvent.getTotalPrice(),
              orderEvent.getOrderStatus(),
              orderEvent.getPaymentStatus().name(),
              orderEvent.getOrderItems()
      );
      mailService.confirmOrder(orderVm);
      log.info("Email xác nhận đơn hàng đã được gửi cho đơn hàng {}", orderEvent.getOrderId());
    } catch (Exception e) {
      log.error("Lỗi khi gửi email xác nhận đơn hàng cho đơn hàng {}: {}", orderEvent.getOrderId(), e.getMessage());
    }
  }

}
