package com.vantruong.rating.service;

import com.vantruong.common.exception.ExternalServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
abstract class AbstractCircuitBreakerFallbackHandler {

  protected void handleBodilessFallback(Throwable throwable) throws Throwable {
    handleError(throwable);
  }

  protected <T> T handleTypedFallback(Throwable throwable) throws Throwable {
    handleError(throwable);
    return null;
  }

  private void handleError(Throwable throwable) throws Throwable {
    log.error("Circuit breaker records an error. Detail {}", throwable.getMessage());
    throw new ExternalServiceException(
            HttpStatus.SERVICE_UNAVAILABLE.value(),
            "Service is currently unavailable. Please try again later."
    );
  }
}
