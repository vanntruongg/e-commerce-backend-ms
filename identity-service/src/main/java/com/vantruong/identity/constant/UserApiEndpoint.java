package com.vantruong.identity.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.vantruong.identity.constant.CommonApiEndpoint.*;

@NoArgsConstructor(access = AccessLevel.NONE)
public class UserApiEndpoint {
  public static final String USERS = "/users";
  public static final String COUNT = "/count";
  public static final String CHANGE_PASSWORD = "/change-password";
  public static final String PHONE = "/phone";
  public static final String ADDRESS = "/address";
  public static final String EMAIL_PARAM = "/{email}";
  public static final String PHONE_PARAM = "/{phone}";
  public static final String ADDRESS_PARAM = "/{address}";
  public static final String REQUEST_VERIFY = "/request/verify";
  public static final String FORGOT_PASSWORD = "/forgot-password";
  public static final String RESET_PASSWORD = "/reset-password";
  public static final String VERIFY_EMAIL = "/verify-email";
  public static final String PROFILE = "/profile";
  public static final String DEFAULT = "/default";
  public static final String USER_GET_BY_EMAIL = GET + EMAIL_PARAM;
  public static final String UPDATE_USER = UPDATE;
  public static final String COUNT_USER = COUNT;
  public static final String USER_CHANGE_PASSWORD = CHANGE_PASSWORD;
  public static final String DELETE_USER = DELETE + EMAIL_PARAM;
  public static final String UPDATE_PHONE = UPDATE + PHONE + PHONE_PARAM;
  public static final String UPDATE_ADDRESS = UPDATE + ADDRESS + ADDRESS_PARAM;
  public static final String GET_ADDRESS = GET + ADDRESS;
  public static final String GET_ADDRESS_BY_ID = GET + ADDRESS + ID_PARAM;
  public static final String GET_DEFAULT_ADDRESS = GET + DEFAULT + ADDRESS;
}
