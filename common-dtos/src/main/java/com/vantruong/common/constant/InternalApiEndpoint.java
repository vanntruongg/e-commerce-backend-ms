package com.vantruong.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class InternalApiEndpoint {
  public static final String NOTIFICATION = "/notification";
  public static final String INTERNAL = "/internal";
  public static final String PRODUCT = "/product";
  public static final String IDENTITY = "/identity";
  public static final String CART = "/cart";
  public static final String MAIL = "/mail";
  public static final String ORDER = "/order";
  public static final String ADDRESS = "/address";
  public static final String USER = "/user";
  public static final String PAYMENTS = "/payments";
  public static final String INVENTORY = "/inventory";
  public static final String PRODUCT_SERVICE_URL = "http://localhost:9002" + INTERNAL + PRODUCT;
  public static final String IDENTITY_SERVICE_URL = "http://localhost:9001" + INTERNAL + IDENTITY;
  public static final String ADDRESS_SERVICE_URL = "http://localhost:9006" + INTERNAL + ADDRESS;
  public static final String CART_SERVICE_URL = "http://localhost:9004" + INTERNAL + CART;
  public static final String ORDER_SERVICE_URL = "http://localhost:9005" + INTERNAL + ORDER;
  public static final String MAIL_SERVICE_URL = "http://localhost:9003" + NOTIFICATION + INTERNAL + MAIL;
  public static final String PAYMENT_SERVICE_URL = "http://localhost:9007" + INTERNAL +  PAYMENTS;
  public static final String INVENTORY_SERVICE_URL = "http://localhost:9008" + INTERNAL + INVENTORY;
  //  params

  // actions
  public static final String DELETE = "/delete";
  public static final String GET = "/get";
  public static final String PAY = "/pay";
  public static final String PRODUCT_IDS = "/product-ids";
  public static final String ID_PARAM = "/{id}";
  public static final String EMAIL_PARAM = "/{email}";
  public static final String CONFIRM = "/confirm";
  public static final String ITEMS = "/items";
  public static final String UPDATE = "/update";
  public static final String CHECK = "/check";
  public static final String QUANTITY = "/quantity";
  public static final String LIST = "/list";
  public static final String PAYMENT_URL = "/payment-url";
  public static final String CALCULATE = "/calculate";
  public static final String STATUS = "/status";

  // inventory

  public static final String PRODUCT_UPDATE_QUANTITY = UPDATE + QUANTITY;
  public static final String CHECK_PRODUCT_QUANTITY = CHECK + PRODUCT + QUANTITY;
  public static final String CHECK_LIST_PRODUCT_QUANTITY = CHECK + LIST + PRODUCT + QUANTITY;
  public static final String CHECK_ORDER_BY_USER_PRODUCT_STATUS = CHECK + USER + PRODUCT + STATUS + EMAIL_PARAM + ID_PARAM;

//  cart
  public static final String CART_DELETE_ITEMS = DELETE + ITEMS;

//  notification
  public static final String MAIL_CONFIRM_ORDER = CONFIRM + ORDER;

//  order payment
  public static final String GET_PAYMENT_URL = GET + PAYMENT_URL;


//  product - inventory
  public static final String GET_ALL_BY_PRODUCT_IDS = GET + PRODUCT_IDS;
  public static final String GET_BY_PRODUCT_ID = GET + ID_PARAM;
  public static final String CALCULATE_BY_PRODUCT_IDS = CALCULATE + PRODUCT_IDS;
}
