package com.vantruong.order.exception;

public class ErrorCode {

  public static final Integer NOT_NULL = 300;
  public static final Integer NOT_FOUND = 404;
  public static final Integer NULL = 405;
  public static final Integer DENIED = 403;
  public static final Integer EXPIRED = 101;
  public static final Integer UNPROCESSABLE_ENTITY = 422;
  public static final int INSUFFICIENT_PRODUCT_QUANTITY = 1001;

  public static final int PRODUCT_CREATION_FAILED = 1002;
  public static final int INVENTORY_CREATION_FAILED = 1003;
}
