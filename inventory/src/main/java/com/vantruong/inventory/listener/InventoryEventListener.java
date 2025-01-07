package com.vantruong.inventory.listener;

import com.vantruong.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryEventListener {
  private final InventoryService inventoryService;
  private final KafkaTemplate<String, Object> kafkaTemplate;


  private static final String UPDATE_INVENTORY = "update-inventory";
  private static final String ROLLBACK_INVENTORY = "rollback-inventory";

  @KafkaListener(topics = UPDATE_INVENTORY)
  public void updateInventory() {

  }

  @KafkaListener(topics = ROLLBACK_INVENTORY)
  public void compensateInventory() {

  }
}
