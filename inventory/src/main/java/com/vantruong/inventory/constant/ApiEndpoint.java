package com.vantruong.inventory.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {

  //  params
  public static final String ID_PARAM = "/{id}";
  // actions
  public static final String GET = "/get";
  public static final String UPDATE = "/update";

  public static final String INVENTORY = "/inventory";
  public static final String SIZE = "/size";
  public static final String ID = "/id";
}
