package com.vantruong.user.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class RoleApiEndpoint {
  public static final String ROLE = "/roles";
  public static final String ROLE_PARAM = "/{role}";
  public static final String DELETE_ROLE = CommonApiEndpoint.DELETE + ROLE_PARAM;

}
