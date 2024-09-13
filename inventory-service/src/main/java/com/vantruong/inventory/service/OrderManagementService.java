package com.vantruong.inventory.service;

import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.dto.order.OrderItemCommonDto;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.event.OrderEvent;
import com.vantruong.common.event.OrderEventStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
  private final InventoryService inventoryService;
  private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

  @KafkaListener(topics = KafkaTopics.INVENTORY_TOPIC)
  public void processInventoryRequest(OrderEvent orderEvent) {
    boolean inventoryUpdated = inventoryService.updateQuantity(converterToProductQuantityRequest(orderEvent), false);
    if (inventoryUpdated) {
      orderEvent.setOrderEventStatus(OrderEventStatus.NEW);
    } else {
      orderEvent.setOrderEventStatus(OrderEventStatus.ROLLBACK);
    }
    kafkaTemplate.send(KafkaTopics.INVENTORY_RESPONSE, orderEvent);
  }

  private void compensateInventory(OrderEvent orderEvent) {
    inventoryService.updateQuantity(converterToProductQuantityRequest(orderEvent), true);
  }

  private List<ProductQuantityRequest> converterToProductQuantityRequest(OrderEvent orderEvent) {
    Set<OrderItemCommonDto> orderDetails = orderEvent.getOrderItems();
    return orderDetails.stream()
            .map(orderItemDto -> ProductQuantityRequest.builder()
                    .productId(orderItemDto.productId())
                    .size(orderItemDto.productSize())
                    .quantity(orderItemDto.quantity())
                    .build())
            .toList();
  }
}
