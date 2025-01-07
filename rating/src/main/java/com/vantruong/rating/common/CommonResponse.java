package com.vantruong.rating.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommonResponse<T> {
  @Builder.Default
  int code = 9008;
  private boolean isSuccess;
  private String message;
  private T data;
  private String errorDetails;
}
