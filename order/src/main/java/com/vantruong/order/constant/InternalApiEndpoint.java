package com.vantruong.order.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class InternalApiEndpoint {
  public static final String INVENTORY_SERVICE_URL = "http://localhost:9004/inventory";
  public static final String PRODUCT_SERVICE_URL = "http://localhost:9003/product";
  // actions
  public static final String ID_PARAM = "/{id}";
  public static final String EMAIL_PARAM = "/{email}";
  public static final String CHECK = "/check";
  public static final String STATUS = "/status";
  public static final String USER_PARAM = "/user/{email}";
  public static final String PRODUCT_PARAM = "/product/{id}";
  public static final String INTERNAL = "/internal";
  public static final String LIST = "/list";
  public static final String PRODUCT = "/product";
  public static final String QUANTITY = "/quantity";
  public static final String CALCULATE = "/calculate";
  public static final String PRODUCT_IDS = "/product-ids";

  public static final String CHECK_ORDER_BY_USER_PRODUCT_STATUS = CHECK + USER_PARAM + PRODUCT_PARAM + STATUS;
  public static final String CHECK_LIST_PRODUCT_QUANTITY = CHECK + LIST + PRODUCT + QUANTITY;
  public static final String CALCULATE_BY_PRODUCT_IDS = CALCULATE + PRODUCT_IDS;




}
