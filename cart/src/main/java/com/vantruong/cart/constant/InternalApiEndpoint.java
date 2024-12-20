package com.vantruong.cart.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class InternalApiEndpoint {

  public static final String PRODUCT = "/product";
  public static final String INVENTORY = "/inventory";
  public static final String INTERNAL = "/internal";
  public static final String IDS = "/ids";
  public static final String ID = "/id";
  public static final String ID_PARAM = "/{id}";
  public static final String GET = "/get";
  public static final String STOCK = "/stock";
  public static final String CHECK = "/check";
  public static final String QUANTITY = "/quantity";
  public static final String GET_PRODUCT_QUANTITY = GET + QUANTITY;
  public static final String PRODUCT_GET_BY_IDS = GET + IDS;
  public static final String PRODUCT_SERVICE_URL = "http://localhost:9003" + INTERNAL + PRODUCT;
  public static final String INVENTORY_SERVICE_URL = "http://localhost:9004" + INTERNAL + INVENTORY;
  public static final String CHECK_PRODUCT_QUANTITY = CHECK + PRODUCT + QUANTITY;


}
