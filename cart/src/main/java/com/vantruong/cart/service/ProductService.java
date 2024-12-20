package com.vantruong.cart.service;

import com.vantruong.cart.repository.client.ProductClient;
import com.vantruong.cart.viewmodel.ProductVm;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService extends AbstractCircuitBreakerFallbackHandler {
  private final ProductClient productClient;

  @Retry(name = "restApi")
  @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "getProductByIdsFallback")
  public List<ProductVm> getProductByIds(List<Long> itemIds) {
    return productClient.getProductByIds(itemIds).getData();
  }

  protected Integer getProductByIdsFallback(Throwable throwable) throws Throwable {
    return handleTypedFallback(throwable);
  }
}
