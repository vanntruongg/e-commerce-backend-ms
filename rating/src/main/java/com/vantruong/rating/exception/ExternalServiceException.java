package com.vantruong.rating.exception;

public class ExternalServiceException extends RuntimeException {
  private final transient ErrorDetail errorDetail;

  public ExternalServiceException(int errorCode, String message) {
    super(message);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }

  public ExternalServiceException(int errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }
}