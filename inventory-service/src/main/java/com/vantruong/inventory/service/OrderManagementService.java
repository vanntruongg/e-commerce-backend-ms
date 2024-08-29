package com.vantruong.inventory.service;

import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.entity.OrderDetail;
import com.vantruong.common.event.OrderEvent;
import com.vantruong.common.event.OrderEventStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderManagementService {
  private final InventoryService inventoryService;
  private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

  @KafkaListener(topics = KafkaTopics.INVENTORY_TOPIC)
  public void processInventoryRequest(OrderEvent orderEvent) {
      boolean inventoryUpdated = inventoryService.updateQuantity(converterToProductQuantityRequest(orderEvent), false);
      if(inventoryUpdated) {
        orderEvent.setOrderStatus(OrderEventStatus.NEW);
      } else {
        orderEvent.setOrderStatus(OrderEventStatus.ROLLBACK);
      }
      kafkaTemplate.send(KafkaTopics.INVENTORY_RESPONSE, orderEvent);
  }

  private void compensateInventory(OrderEvent orderEvent) {
    inventoryService.updateQuantity(converterToProductQuantityRequest(orderEvent), true);
  }

  private List<ProductQuantityRequest> converterToProductQuantityRequest(OrderEvent orderEvent) {
    List<OrderDetail> orderDetails = orderEvent.getOrderDetails();
    return orderDetails.stream()
            .map(orderDetail -> ProductQuantityRequest.builder()
                    .productId(orderDetail.getProductId())
                    .size(orderDetail.getProductSize())
                    .quantity(orderDetail.getQuantity())
                    .build())
            .toList();
  }
}
