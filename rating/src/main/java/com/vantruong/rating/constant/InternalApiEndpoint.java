package com.vantruong.rating.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class InternalApiEndpoint {

  //  params
  public static final String INTERNAL = "/internal";
  public static final String ORDER_SERVICE_URL = "http://localhost:9006/order";
  public static final String USER_SERVICE_URL = "http://localhost:9002/user";

  public static final String CHECK = "/check";
  public static final String STATUS = "/status";
  public static final String USER_PARAM = "/user/{email}";
  public static final String PRODUCT_PARAM = "/product/{id}";
  public static final String CHECK_ORDER_BY_USER_PRODUCT_STATUS = CHECK + USER_PARAM + PRODUCT_PARAM + STATUS;


}
