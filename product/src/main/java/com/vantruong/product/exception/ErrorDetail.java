package com.vantruong.product.exception;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {

  private int errorCode;
  private String message;

}
