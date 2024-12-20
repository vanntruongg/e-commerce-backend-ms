package com.vantruong.cart.exception;

import com.vantruong.cart.common.CommonResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<CommonResponse<Object>> handleNotFoundException(WebRequest request, Exception exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CommonResponse.builder()
                    .isSuccess(false)
                    .message(exception.getMessage())
                    .errorDetails(exception.getCause() != null ? exception.getCause().getMessage() : StringUtils.EMPTY)
            .build());
  }
  @ExceptionHandler(value = InsufficientProductQuantityException.class)
  public ResponseEntity<CommonResponse<Object>> handleInsufficientProductQuantityException(WebRequest request, Exception exception) {
    return ResponseEntity.status(ErrorCode.UNPROCESSABLE_ENTITY).body(CommonResponse.builder()
                    .isSuccess(false)
                    .message(exception.getMessage())
                    .errorDetails(exception.getCause() != null ? exception.getCause().getMessage() : StringUtils.EMPTY)
            .build());
  }
}
