package com.vantruong.order.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {
  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String SUCCESS = "successfully!";

  //  actions
  public static final String FIND = "Find";
  public static final String PRODUCT_QUANTITY_NOT_AVAILABLE = "The product quantity is not available.";
  public static final String ACCESS_DENIED = "You are not allowed to perform this action.";
  private static final String UPDATE = "Update";

  //  status
  public static final String NOT_FOUND = "Not found!";

  public static final String PAYMENT_METHOD = "Payment method";
  public static final String ORDER = "Place order";


  public static final String GET_LINK_PAYMENT_SUCCESS = "Get payment url successfully!";
  public static final String ORDER_SUCCESS = String.format(CHAR_SEQUENCE_2, ORDER, SUCCESS);
  public static final String PAYMENT_METHOD_NOT_FOUND = String.format(CHAR_SEQUENCE_2, NOT_FOUND, PAYMENT_METHOD);
  public static final String FIND_SUCCESS = String.format(CHAR_SEQUENCE_2, FIND, SUCCESS);
  public static final String UPDATE_SUCCESS = String.format(CHAR_SEQUENCE_2, UPDATE, SUCCESS);
  public static final String ORDER_NOT_FOUND = "Order not found!";


  //  actions
  public static final String PAYMENT_SUCCESS = "Payment successfully!";
  //  status

}
