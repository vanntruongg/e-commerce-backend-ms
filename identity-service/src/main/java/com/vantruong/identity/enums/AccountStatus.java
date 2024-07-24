package com.vantruong.identity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatus {
  PENDING_VERIFICATION,
  ACTIVE,
  LOCKED,
  DELETED;
}