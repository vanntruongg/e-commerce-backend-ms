package com.vantruong.payment.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

  public static final String PAYMENTS = "/payments";
  public static final String METHODS = "/methods";
  public static final String PAY = "/pay";
  public static final String GET_URL_PAYMENT = "/get-link-payment";


  public static final String GET = "/get";
  public static final String UPDATE = "/update";
  public static final String STATUS = "/status";
  public static final String ORDER = "/order";
  public static final String ID_PARAM = "/{id}";
  public static final String GET_BY_ORDER_ID = GET + ORDER + ID_PARAM;
  public static final String UPDATE_PAYMENT_STATUS = UPDATE + STATUS + ID_PARAM;
}
