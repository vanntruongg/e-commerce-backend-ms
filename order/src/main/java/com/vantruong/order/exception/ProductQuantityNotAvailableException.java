package com.vantruong.order.exception;

public class ProductQuantityNotAvailableException extends RuntimeException {
  private final transient ErrorDetail errorDetail;

  public ProductQuantityNotAvailableException(int errorCode, String message) {
    super(message);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }

  public ProductQuantityNotAvailableException(int errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }
}
