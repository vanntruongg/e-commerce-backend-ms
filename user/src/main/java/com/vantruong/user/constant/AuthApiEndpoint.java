package com.vantruong.user.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class AuthApiEndpoint {
  public static final String IDENTITY = "/identity";
  public static final String AUTH = "/auth";
  public static final String LOGIN = "/login";
  public static final String LOGOUT = "/logout";
  public static final String REGISTER = "/register";
  public static final String REFRESH_TOKEN = "/refresh-token";
}
