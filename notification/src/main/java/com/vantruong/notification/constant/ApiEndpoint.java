package com.vantruong.notification.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {
  public static final String NOTIFICATION = "/notification";
  public static final String MAIL = "/mail";
  public static final String VERIFY = "/verify";
  public static final String FORGOT_PASSWORD = "/forgot-password";
  public static final String CONFIRM = "/confirm";
  public static final String ORDER = "/order";
}
