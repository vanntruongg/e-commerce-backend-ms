package com.vantruong.book.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

//  params

  public static final String ID_PARAM = "/{id}";
  public static final String NAME = "/name";

  // actions
  public static final String GET = "/get";
  public static final String CREATE = "/create";
  public static final String UPDATE = "/update";
  public static final String COUNT = "/count";

  public static final String BOOK = "/book";
  public static final String STOCK = "/stock";
  public static final String BOOKS = "/books";
  public static final String CATEGORY = "/category";
  public static final String SUB_CATEGORY = "/subcategory";
  public static final String ALL_LEVEL = "/all-level";
  public static final String TOP_LEVEL = "/top-level";
  public static final String LIMIT = "/limit";
  public static final String LIMIT_PARAM = "/{limit}";

  public static final String GET_ALL = "/get-all";
  public static final String INTERNAL = "/internal";
  public static final String IDS = "/ids";
  public static final String GET_BY_IDS = GET + IDS;
  public static final String GET_BY_ID = GET + ID_PARAM;
  public static final String GET_BY_CATEGORY_ID = GET + CATEGORY + ID_PARAM + LIMIT + LIMIT_PARAM;
  public static final String GET_BY_NAME = GET + NAME;
  public static final String CATEGORY_GET_SUBCATEGORY = GET + SUB_CATEGORY + ID_PARAM;
  public static final String CATEGORY_GET_SUBCATEGORY_ALL_LEVEL = GET + SUB_CATEGORY + ALL_LEVEL + ID_PARAM;
  public static final String GET_STOCK_BY_ID = GET + STOCK + ID_PARAM;
  public static final String GET_TOP_LEVEL_CATEGORY = GET + TOP_LEVEL;
}
