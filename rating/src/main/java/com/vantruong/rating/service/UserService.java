package com.vantruong.rating.service;

import com.vantruong.rating.client.UserClient;
import com.vantruong.rating.common.CommonResponse;
import com.vantruong.rating.viewmodel.UserVm;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractCircuitBreakerFallbackHandler {
  private final UserClient userClient;

  @Retry(name = "userServiceApi")
  @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "getUserFallback")
  public UserVm getUser() {
    CommonResponse<UserVm> response = userClient.getUserProfile();
    return response.getData();
  }

  protected UserVm getUserFallback(Throwable throwable) throws Throwable {
    return handleTypedFallback(throwable);
  }
}
