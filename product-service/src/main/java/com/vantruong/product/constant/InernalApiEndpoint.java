package com.vantruong.product.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class InernalApiEndpoint {

//  params

  // actions
  public static final String GET = "/get";
  public static final String UPDATE = "/update";

  public static final String PRODUCT = "/product";
  public static final String STOCK = "/stock";
  public static final String INTERNAL = "/internal";
  public static final String ID = "/id";
  public static final String ID_PARAM = "/{id}";
  public static final String IDS = "/ids";
  public static final String QUANTITY = "/quantity";
  public static final String GET_BY_IDS = GET + IDS;
  public static final String UPDATE_QUANTITY = UPDATE + QUANTITY;
  public static final String GET_STOCK_BY_ID = GET + STOCK + ID_PARAM;
  public static final String GET_BY_ID = GET + ID_PARAM;

}
