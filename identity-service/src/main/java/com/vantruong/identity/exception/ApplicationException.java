package com.vantruong.identity.exception;

import com.vantruong.common.exception.ErrorDetail;
import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {
  private final transient ErrorDetail errorDetail;
  public ApplicationException(int errorCode, String errorMessage) {
    super(errorMessage);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(errorMessage)
            .build();
  }
  public ApplicationException(int errorCode, String errorMessage, Throwable cause) {
    super(errorMessage, cause);
    this.errorDetail = ErrorDetail.builder()
            .errorCode(errorCode)
            .message(errorMessage)
            .build();
  }
}
