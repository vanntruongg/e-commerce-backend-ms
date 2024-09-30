package com.vantruong.rating.exception;

import com.vantruong.common.exception.AccessDeniedException;
import com.vantruong.common.exception.Constant;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.common.exception.ResourceExistedException;
import com.vantruong.rating.common.CommonResponse;
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

  @ExceptionHandler(value = ResourceExistedException.class)
  public ResponseEntity<CommonResponse<Object>> handleResourceExistedException(WebRequest request, Exception exception) {
    return ResponseEntity.status(Constant.ErrorCode.RESOURCE_ALREADY_EXISTED).body(CommonResponse.builder()
            .isSuccess(false)
            .message(exception.getMessage())
            .errorDetails(exception.getCause() != null ? exception.getCause().getMessage() : StringUtils.EMPTY)
            .build());
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  public ResponseEntity<CommonResponse<Object>> handleAccessDeniedException(WebRequest request, Exception exception) {
    return ResponseEntity.status(Constant.ErrorCode.DENIED).body(CommonResponse.builder()
            .isSuccess(false)
            .message(exception.getMessage())
            .errorDetails(exception.getCause() != null ? exception.getCause().getMessage() : StringUtils.EMPTY)
            .build());
  }
}
