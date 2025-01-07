package com.vantruong.inventory.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class InternalApiEndpoint {
  public static final String INTERNAL = "/internal";
  public static final String INVENTORY = "/inventory";
  public static final String CREATE = "/create";
  public static final String GET = "/get";
  public static final String CHECK = "/check";
  public static final String PRODUCT = "/product";
  public static final String QUANTITY = "/quantity";
  public static final String LIST = "/list";
  public static final String ID_PARAM = "/{id}";
  public static final String PRODUCT_IDS = "/product-ids";
  public static final String CREATE_INVENTORY = CREATE + INVENTORY;
  public static final String CHECK_PRODUCT_QUANTITY = CHECK + PRODUCT + QUANTITY;
  public static final String CHECK_LIST_PRODUCT_QUANTITY = CHECK + LIST + PRODUCT + QUANTITY;
  public static final String GET_ALL_BY_PRODUCT_IDS = GET + PRODUCT_IDS;
  public static final String GET_BY_PRODUCT_ID = GET + ID_PARAM;

}
