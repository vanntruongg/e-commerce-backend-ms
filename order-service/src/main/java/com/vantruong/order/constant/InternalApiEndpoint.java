package com.vantruong.order.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class InternalApiEndpoint {
  public static final String NOTIFICATION = "/notification";
  public static final String INTERNAL = "/internal";
  public static final String PRODUCT = "/product";
  public static final String CART = "/cart";
  public static final String MAIL = "/mail";
  public static final String ORDER = "/order";
  public static final String ADDRESS = "/address";
  public static final String USER = "/user";
  public static final String PAYMENTS = "/payments";
  public static final String PRODUCT_SERVICE_URL = "http://localhost:9002" + INTERNAL + PRODUCT;
  public static final String ADDRESS_SERVICE_URL = "http://localhost:9006" + INTERNAL + ADDRESS;
  public static final String CART_SERVICE_URL = "http://localhost:9004" + INTERNAL + CART;
  public static final String MAIL_SERVICE_URL = "http://localhost:9003" + NOTIFICATION + INTERNAL + MAIL;
  public static final String PAYMENT_SERVICE_URL = "http://localhost:9007" + PAYMENTS;
  //  params

  // actions
  public static final String DELETE = "/delete";
  public static final String GET = "/get";
  public static final String PAY = "/pay";
  public static final String ID_PARAM = "/{id}";
  public static final String CONFIRM = "/confirm";
  public static final String ITEMS = "/items";
  public static final String UPDATE = "/update";
  public static final String QUANTITY = "/quantity";

  public static final String PRODUCT_UPDATE_QUANTITY = UPDATE + QUANTITY;
  public static final String CART_DELETE_ITEMS = DELETE + ITEMS;

  public static final String MAIL_CONFIRM_ORDER = CONFIRM + ORDER;


}
