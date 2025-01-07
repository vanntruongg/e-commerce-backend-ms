package com.vantruong.order.service;

import com.vantruong.order.client.ProductClient;
import com.vantruong.order.viewmodel.CalculateTotalOrderPricePostVm;
import com.vantruong.order.viewmodel.CalculateTotalOrderPriceVm;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService extends AbstractCircuitBreakerFallbackHandler {
  private final ProductClient productClient;

  @Retry(name = "restApi")
  @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "calculateTotalOrderPriceFallback")
  public CalculateTotalOrderPriceVm calculateTotalOrderPrice(CalculateTotalOrderPricePostVm totalOrderPricePostVm) {
    return productClient.calculateTotalOrderPrice(totalOrderPricePostVm).getData();
  }

  protected Double calculateTotalOrderPriceFallback(Throwable throwable) throws Throwable {
    return handleTypedFallback(throwable);
  }
}
