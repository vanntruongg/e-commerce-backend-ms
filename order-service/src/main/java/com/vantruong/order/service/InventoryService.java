package com.vantruong.order.service;

import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.order.client.InventoryClient;
import com.vantruong.order.client.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService extends AbstractCircuitBreakerFallbackHandler {
  private final InventoryClient inventoryClient;

  public Boolean checkListProductQuantity(List<ProductQuantityRequest> requests) {
    return inventoryClient.checkListProductQuantity(requests).getData();
  }
}
