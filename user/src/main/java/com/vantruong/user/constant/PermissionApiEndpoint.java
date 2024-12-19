package com.vantruong.user.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class PermissionApiEndpoint {
  public static final String PERMISSIONS = "/permissions";
  public static final String PERMISSION_PARAM = "/{permission}";
  public static final String DELETE_PERMISSION = CommonApiEndpoint.DELETE + PERMISSION_PARAM;

}
