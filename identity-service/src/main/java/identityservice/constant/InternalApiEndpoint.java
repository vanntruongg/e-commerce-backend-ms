package identityservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class InternalApiEndpoint {

  public static final String NOTIFICATION = "/notification";
  public static final String INTERNAL = "/internal";
  public static final String MAIL = "/mail";
  public static final String MAIL_SERVICE_URL = "http://localhost:9003" + NOTIFICATION + INTERNAL + MAIL;
  public static final String ADDRESS_DATA = "/address-data";
  public static final String GET = "/get";
  public static final String ADDRESS_SERVICE_URL = "http://localhost:9006" + ADDRESS_DATA;
  public static final String VERIFY = "/verify";
  public static final String FORGOT_PASSWORD = "/forgot-password";
  public static final String INTROSPECT = "/introspect";
  public static final String USER = "/user";
  public static final String EXISTED = "/existed";
  public static final String USER_EXISTED_BY_EMAIL = USER + EXISTED;

}
