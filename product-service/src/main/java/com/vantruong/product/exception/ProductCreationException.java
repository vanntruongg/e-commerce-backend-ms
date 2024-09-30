package com.vantruong.product.exception;

import com.vantruong.common.exception.ErrorDetail;

public class ProductCreationException extends RuntimeException {
  private final transient ErrorDetail errorDetail;

  public ProductCreationException(int errorCode, String message) {
    super(message);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }

  public ProductCreationException(int errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }
}
