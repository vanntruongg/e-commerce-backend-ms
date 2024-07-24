package com.vantruong.identity.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SendMailVerifyUserRequest {
  private String email;
  private String name;
  private String token;
}
