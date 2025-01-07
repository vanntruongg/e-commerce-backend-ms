package com.vantruong.user.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {
  public static final String ADDRESS = "/address";
  public static final String GET = "/get";
  public static final String REGISTER = "/register";
  public static final String DATA = "/data";
  public static final String USER = "/user";
  public static final String DEFAULT = "/default";
  public static final String UPDATE = "/update";
  public static final String CREATE = "/create";
  public static final String VALIDATE = "/validate";
  public static final String ID_PARAM = "/{id}";
  public static final String DELETE = "/delete";
  public static final String ADDRESS_ID = "addressId";
  public static final String PROVINCE_ID = "provinceId";
  public static final String DISTRICT_ID = "districtId";
  public static final String WARD_ID = "wardId";

  public static final String GET_DATA = GET + DATA;
}
