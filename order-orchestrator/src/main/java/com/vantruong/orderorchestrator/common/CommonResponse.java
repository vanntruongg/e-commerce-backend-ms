package com.vantruong.orderorchestrator.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonResponse<T> {
  @Builder.Default
  int code = 9009;
  boolean isSuccess;
  String message;
  T data;
  String errorDetails;
}
