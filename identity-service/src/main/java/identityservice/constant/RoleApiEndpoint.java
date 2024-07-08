package identityservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static identityservice.constant.CommonApiEndpoint.DELETE;

@NoArgsConstructor(access = AccessLevel.NONE)
public class RoleApiEndpoint {
  public static final String ROLE = "/roles";
  public static final String ROLE_PARAM = "/{role}";
  public static final String DELETE_ROLE = DELETE + ROLE_PARAM;

}
