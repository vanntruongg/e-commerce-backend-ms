package com.vantruong.cart.service;

import com.vantruong.cart.repository.client.InventoryClient;
import com.vantruong.cart.viewmodel.ProductQuantityCheckVm;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService extends AbstractCircuitBreakerFallbackHandler {
  private final InventoryClient inventoryClient;

  @Retry(name = "restApi")
  @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "checkProductQuantityByIdFallback")
  public Integer checkProductQuantityById(ProductQuantityCheckVm request) {
    return inventoryClient.checkProductQuantityById(request).getData();
  }

  protected Integer checkProductQuantityByIdFallback(Throwable throwable) throws Throwable {
    return handleTypedFallback(throwable);
  }
}
