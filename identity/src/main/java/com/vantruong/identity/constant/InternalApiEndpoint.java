package com.vantruong.identity.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class InternalApiEndpoint {
  public static final String INTERNAL = "/internal";
  public static final String USER_SERVICE_URL = "http://localhost:9002";
  public static final String NOTIFICATION_SERVICE_URL = "http://localhost:9009";
  public static final String INTROSPECT_TOKEN = "/introspect";

}
