package addressdataservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ApiEndpoint {
  public static final String ADDRESS = "/address";
  public static final String GET = "/get";
  public static final String DATA = "/data";
  public static final String USER = "/user";
  public static final String DEFAULT = "/default";
  public static final String UPDATE = "/update";
  public static final String CREATE = "/create";
  public static final String EMAIL_PARAM = "/{email}";
  public static final String VARIABLE_EMAIL = "email";
  public static final String VARIABLE_ID = "id";

  public static final String ID_PARAM = "/{id}";
  public static final String DELETE = "/delete";
}
