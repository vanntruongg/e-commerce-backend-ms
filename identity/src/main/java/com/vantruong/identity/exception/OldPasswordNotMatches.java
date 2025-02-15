package com.vantruong.identity.exception;

public class OldPasswordNotMatches extends RuntimeException {
  private final transient ErrorDetail errorDetail;

  public OldPasswordNotMatches(int errorCode, String message) {
    super(message);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }

  public OldPasswordNotMatches(int errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(message)
            .build();
  }
}
