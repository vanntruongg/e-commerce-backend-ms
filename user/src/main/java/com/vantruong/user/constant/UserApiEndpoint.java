package com.vantruong.user.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.vantruong.user.constant.ApiEndpoint.GET;
import static com.vantruong.user.constant.ApiEndpoint.ID_PARAM;
import static com.vantruong.user.constant.CommonApiEndpoint.ACTIVE;
import static com.vantruong.user.constant.MessageConstant.DELETE;
import static com.vantruong.user.constant.MessageConstant.UPDATE;

@NoArgsConstructor(access = AccessLevel.NONE)
public class UserApiEndpoint {
  public static final String USERS = "/users";
  public static final String COUNT = "/count";
  public static final String CHANGE_PASSWORD = "/change-password";
  public static final String PHONE = "/phone";
  public static final String ADDRESS = "/address";
  public static final String EMAIL_PARAM = "/{email}";
  public static final String PHONE_PARAM = "/{phone}";
  public static final String FORGOT_PASSWORD = "/forgot-password";
  public static final String RESET_PASSWORD = "/reset-password";
  public static final String PROFILE = "/profile";
  public static final String SEARCH = "/search";
  public static final String NAME = "/name";
  public static final String NAME_PARAM = "/{name}";
  public static final String USER_GET_BY_EMAIL = GET + EMAIL_PARAM;
  public static final String UPDATE_USER = UPDATE;
  public static final String COUNT_USER = COUNT;
  public static final String USER_CHANGE_PASSWORD = CHANGE_PASSWORD;
  public static final String DELETE_USER = DELETE + EMAIL_PARAM;
  public static final String ACTIVE_ACCOUNT = ACTIVE + EMAIL_PARAM;
  public static final String UPDATE_PHONE = UPDATE + PHONE + PHONE_PARAM;
//  public static final String UPDATE_ADDRESS = UPDATE + ADDRESS + ADDRESS_PARAM;
//  public static final String GET_ADDRESS = GET + ADDRESS;
//  public static final String GET_ADDRESS_BY_ID = GET + ADDRESS + ID_PARAM;
//  public static final String GET_DEFAULT_ADDRESS = GET + DEFAULT + ADDRESS;
  public static final String SEARCH_BY_NAME = SEARCH + NAME + NAME_PARAM;
}