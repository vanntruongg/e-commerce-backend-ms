package com.vantruong.inventory.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

  //  params
  public static final String EMAIL_PARAM = "/{email}";
  public static final String ID_PARAM = "/{id}";

  // actions
  public static final String GET = "/get";
  public static final String CREATE = "/create";

  public static final String INVENTORY = "/inventory";
  public static final String INTERNAL = "/internal";
  public static final String CHECK = "/check";
  public static final String QUANTITY = "/quantity";
  public static final String SIZE = "/size";
  public static final String ID = "/id";
}
