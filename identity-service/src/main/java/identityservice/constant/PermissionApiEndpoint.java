package identityservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static identityservice.constant.CommonApiEndpoint.*;

@NoArgsConstructor(access = AccessLevel.NONE)
public class PermissionApiEndpoint {
  public static final String PERMISSIONS = "/permissions";
  public static final String PERMISSION_PARAM = "/{permission}";
  public static final String DELETE_PERMISSION = DELETE + PERMISSION_PARAM;

}
