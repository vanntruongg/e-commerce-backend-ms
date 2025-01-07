package com.vantruong.order.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {
  public static final String ID_PARAM = "/{id}";
  public static final String STATUS = "/status";

  // actions
  public static final String GET = "/get";
  public static final String CREATE = "/create";

  public static final String ORDER = "/order";
  public static final String ORDERS = "/orders";
  public static final String REVENUE = "/revenue";

  public static final String UPDATE = "/update";
  public static final String STATISTIC = "/statistic";
  public static final String GET_TOTAL_ORDER = "/total-order";
  public static final String MY_ORDER = "/my-order";
  public static final String CREATE_ORDER = CREATE;
  public static final String GET_MY_ORDER = GET + MY_ORDER;
  public static final String GET_BY_STATUS = GET + STATUS;
  public static final String GET_BY_ID = GET + ID_PARAM;
  public static final String UPDATE_STATUS = UPDATE + STATUS;
  public static final String SEARCH = "/search";

  public static final String SEARCH_BY_ID = SEARCH + ID_PARAM;
}
