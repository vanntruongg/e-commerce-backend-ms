package com.vantruong.notification.service;

import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.dto.UserAddress;
import com.vantruong.common.event.OrderEvent;
import com.vantruong.notification.common.CommonResponse;
import com.vantruong.notification.dto.OrderDto;
import com.vantruong.notification.repository.UserAddressClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderManagementService {
  private final MailService mailService;
  private final UserAddressClient userAddressClient;

  @KafkaListener(topics = KafkaTopics.NOTIFICATION_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
  public void processSendMailRequest(OrderEvent orderEvent) {
    try {
      CommonResponse<UserAddress> response = userAddressClient.getUserAddressById(orderEvent.getAddressId());
      UserAddress userAddress = response.getData();

      OrderDto orderDto = OrderDto.builder()
              .email(orderEvent.getEmail())
              .orderId(orderEvent.getOrderId())
              .name(userAddress.getName())
              .address(combineAddress(userAddress))
              .orderStatus(orderEvent.getOrderStatus().name())
              .orderDetail(orderEvent.getOrderDetails())
              .notes(orderEvent.getNotes())
              .totalPrice(orderEvent.getTotalPrice())
              .build();
      mailService.confirmOrder(orderDto);
      log.info("Email xác nhận đơn hàng đã được gửi cho đơn hàng {}", orderEvent.getOrderId());
    } catch (Exception e) {
      log.error("Lỗi khi gửi email xác nhận đơn hàng cho đơn hàng {}: {}", orderEvent.getOrderId(), e.getMessage());
    }
  }

  private String combineAddress(UserAddress userAddress) {
    return String.join(", ",
            userAddress.getStreet(),
            userAddress.getWard(),
            userAddress.getDistrict(),
            userAddress.getProvince()
    );
  }

}
