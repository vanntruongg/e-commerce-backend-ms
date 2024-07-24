package com.vantruong.identity.dto.request;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
  private String oldPassword;
  private String newPassword;
}
