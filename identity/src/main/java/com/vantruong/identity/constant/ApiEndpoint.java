package com.vantruong.identity.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {
  public static final String IDENTITY = "/identity";
  public static final String REGISTER = "/register";
  public static final String LOGIN = "/login";
  public static final String LOGOUT = "/logout";
  public static final String DELETE = "/delete";
  public static final String ACTIVE = "/active";
  public static final String REFRESH_TOKEN = "/refresh-token";

  public static final String EMAIL_PARAM = "/{email}";

  public static final String RESET_PASSWORD = "/reset-password";
  public static final String VERIFY_EMAIL = "/verify-email";
  public static final String REQUEST_VERIFY = "/request/verify";
  public static final String CHANGE_PASSWORD = "/change-password";
  public static final String FORGOT_PASSWORD = "/forgot-password";
  public static final String DELETE_USER = DELETE + EMAIL_PARAM;
  public static final String ACTIVE_ACCOUNT = ACTIVE + EMAIL_PARAM;

}
