package com.vantruong.rating.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

  //  params
  public static final String PRODUCT_ID = "/{productId}";
  public static final String ID_PARAM = "/{id}";
  public static final String PRODUCT = "/product";
  public static final String DELETE = "/delete";
  public static final String CREATE = "/create";
  public static final String GET = "/get";
  public static final String PRODUCTS = "/products";

  public static final String RATINGS = "/ratings";
  public static final String AVERAGE_STAR = "/average-star";
  public static final String BREAKDOWN = "/breakdown";
  public static final String GET_RATING_LIST = GET + PRODUCTS + PRODUCT_ID;
  public static final String GET_AVERAGE_RATING_OF_PRODUCT = GET + PRODUCT + PRODUCT_ID + AVERAGE_STAR;
  public static final String GET_RATING_BREAKDOWN = GET + PRODUCT + PRODUCT_ID + BREAKDOWN;
}
