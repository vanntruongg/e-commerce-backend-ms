package com.vantruong.inventory.listener;

import com.vantruong.common.constant.KafkaTopics;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.event.CancelOrderEvent;
import com.vantruong.common.event.OrderEvent;
import com.vantruong.common.event.OrderEventStatus;
import com.vantruong.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InventoryEventListener {
  private final InventoryService inventoryService;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @KafkaListener(topics = KafkaTopics.ORDER_CREATED_TOPIC)
  public void handleOrderCreated(OrderEvent orderEvent) {
    List<ProductQuantityRequest> request = orderEvent.getOrderItems().stream()
            .map(orderItem -> ProductQuantityRequest.builder()
                    .productId(orderItem.productId())
                    .size(orderItem.productSize())
                    .quantity(orderItem.quantity())
                    .build())
            .toList();
    boolean inventoryUpdated = inventoryService.updateQuantityAndCompensate(request, false);

    OrderEventStatus status = inventoryUpdated ? OrderEventStatus.NEW : OrderEventStatus.ROLLBACK;
    orderEvent.setOrderEventStatus(status);

    kafkaTemplate.send(KafkaTopics.INVENTORY_RESPONSE, orderEvent);
  }

  @KafkaListener(topics = KafkaTopics.ORDER_CANCEL_TOPIC)
  public void handleOrderCanceled(CancelOrderEvent cancelOrderEvent) {
    List<ProductQuantityRequest> request = cancelOrderEvent.getOrderItems().stream()
            .map(orderItem -> ProductQuantityRequest.builder()
                    .productId(orderItem.productId())
                    .size(orderItem.productSize())
                    .quantity(orderItem.quantity())
                    .build())
            .toList();
    inventoryService.updateQuantityAndCompensate(request, true);
  }
}
