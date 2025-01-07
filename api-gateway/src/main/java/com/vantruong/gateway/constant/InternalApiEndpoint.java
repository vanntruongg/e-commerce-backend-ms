package com.vantruong.gateway.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class InternalApiEndpoint {
  public static final String INTERNAL = "/internal";
  public static final String IDENTITY_SERVICE_URL = "http://localhost:9001";
  public static final String INTROSPECT_TOKEN = "/introspect";

}
