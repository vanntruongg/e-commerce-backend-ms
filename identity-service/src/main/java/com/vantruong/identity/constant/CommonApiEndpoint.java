package com.vantruong.identity.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class CommonApiEndpoint {
  public static final String IDENTITY = "/identity";
  public static final String GET = "/get";
  public static final String UPDATE = "/update";
  public static final String DELETE = "/delete";
  public static final String CREATE = "/create";
  public static final String ID_PARAM = "/{id}";
}
