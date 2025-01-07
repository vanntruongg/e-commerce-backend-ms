package com.vantruong.product.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class InternalApiEndpoint {

  public static final String INTERNAL = "/internal";
  public static final String INVENTORY_SERVICE_URL = "http://localhost:9004/inventory";
  // actions
  public static final String GET = "/get";
  public static final String PRODUCT = "/product";
  public static final String IDS = "/ids";
  public static final String GET_BY_IDS = GET + IDS;

  public static final String INVENTORY = "/inventory";
  public static final String CREATE = "/create";
  public static final String ID_PARAM = "/{id}";
  public static final String PRODUCT_IDS = "/product-ids";
  public static final String CALCULATE = "/calculate";
  public static final String CREATE_INVENTORY = CREATE + INVENTORY;
  public static final String GET_ALL_BY_PRODUCT_IDS = GET + PRODUCT_IDS;
  public static final String GET_BY_PRODUCT_ID = GET + ID_PARAM;
  public static final String CALCULATE_BY_PRODUCT_IDS = CALCULATE + PRODUCT_IDS;


}
