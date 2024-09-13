package com.vantruong.common.exception;

public final class Constant {
  public static final class ErrorCode {
    public static final Integer NOT_NULL = 300;
    public static final Integer NOT_FOUND = 404;
    public static final Integer NULL = 405;
    public static final Integer DENIED = 403;
    public static final Integer EXPIRED = 101;
    public static final Integer UNPROCESSABLE_ENTITY = 422;
    public static final Integer FORM_ERROR = 420;
    public static final int RESOURCE_ALREADY_EXISTED = 409;
    public static final int INSUFFICIENT_PRODUCT_QUANTITY = 1001;
  }

  public static final class Message {
    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final String ACCESS_DENIED = "ACCESS_DENIED";
    public static final String RESOURCE_ALREADY_EXISTED = "RESOURCE_ALREADY_EXISTED";
  }
}
