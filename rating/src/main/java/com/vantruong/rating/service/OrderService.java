package com.vantruong.rating.service;

import com.vantruong.common.dto.order.OrderExistsByProductAndUser;
import com.vantruong.rating.client.OrderClient;
import com.vantruong.rating.common.CommonResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService extends AbstractCircuitBreakerFallbackHandler {
  private final OrderClient orderClient;

  @Retry(name = "orderServiceApi")
  @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "checkOrderExistsByProductAndUserWithStatusFallback")
  public OrderExistsByProductAndUser checkOrderExistsByProductAndUserWithStatus(String email, Long productId) {
    CommonResponse<OrderExistsByProductAndUser> response = orderClient.checkOrderExistsByProductAndUserWithStatus(email, productId);
    return response.getData();
  }

  protected OrderExistsByProductAndUser checkOrderExistsByProductAndUserWithStatusFallback(Throwable throwable) throws Throwable {
    return handleTypedFallback(throwable);
  }
}
