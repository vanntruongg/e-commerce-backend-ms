package com.vantruong.product.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

//  params

  public static final String ID_PARAM = "/{id}";
  public static final String NAME = "/name";
  public static final String NAME_PARAM = "/{name}";

  // actions
  public static final String GET = "/get";
  public static final String SEARCH = "/search";
  public static final String CREATE = "/create";
  public static final String UPDATE = "/update";
  public static final String COUNT_PRODUCT = "/count";

  public static final String PRODUCT = "/product";
  public static final String STOCK = "/stock";
  public static final String PRODUCTS = "/products";
  public static final String CATEGORY = "/category";
  public static final String SUB_CATEGORY = "/subcategory";
  public static final String ALL_LEVEL = "/all-level";
  public static final String TOP_LEVEL = "/top-level";
  public static final String LIMIT = "/limit";
  public static final String LIMIT_PARAM = "/{limit}";

  public static final String GET_ALL = "/get-all";
  public static final String INTERNAL = "/internal";
  public static final String IDS = "/ids";
  public static final String PRODUCT_ID = "/{product-id}";
  private static final String ALL_PARENT = "/all-parent";
  public static final String GET_BY_IDS = GET + IDS;
  public static final String PRODUCT_GET_BY_ID = GET + ID_PARAM;
  public static final String PRODUCT_GET_BY_CATEGORY_ID = GET + CATEGORY + ID_PARAM + LIMIT + LIMIT_PARAM;
  public static final String PRODUCT_GET_BY_NAME = GET + NAME;
  public static final String CATEGORY_GET_SUBCATEGORY = GET + SUB_CATEGORY + ID_PARAM;
  public static final String CATEGORY_GET_SUBCATEGORY_ALL_LEVEL = GET + SUB_CATEGORY + ALL_LEVEL + ID_PARAM;
  public static final String PRODUCT_GET_STOCK_BY_ID = GET + STOCK + ID_PARAM;
  public static final String UPDATE_PRODUCT = UPDATE;
  public static final String GET_TOP_LEVEL_CATEGORY = GET + TOP_LEVEL;
  public static final String GET_ALL_PARENT_CATEGORY_BY_ID = GET + ALL_PARENT + ID_PARAM;
  public static final String SEARCH_BY_NAME = SEARCH + NAME + NAME_PARAM;
}
