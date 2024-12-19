package com.vantruong.product.common;

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
  int code = 9002;
  private boolean isSuccess;
  private String message;
  private T data;
  private String errorDetails;
}
