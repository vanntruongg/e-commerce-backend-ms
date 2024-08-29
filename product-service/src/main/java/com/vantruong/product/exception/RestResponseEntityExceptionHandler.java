package com.vantruong.product.exception;

import com.vantruong.common.exception.NotFoundException;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.vantruong.product.common.CommonResponse;

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
}
