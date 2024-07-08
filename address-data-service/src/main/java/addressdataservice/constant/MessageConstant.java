package addressdataservice.constant;

import jakarta.ws.rs.DELETE;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {

  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String CHAR_SEQUENCE_3 = "%s %s %s";
  public static final String SUCCESS = "Successfully!";
  public static final String FAIL = "fail!";

  public static final String FIND_SUCCESS = SUCCESS;
  public static final String ADDRESS = "address";
  public static final String UPDATE = "Update";
  public static final String DELETE = "Delete";
  public static final String NOT_FOUND = "Not found!";
  public static final String CREATE_ADDRESS_SUCCESS = "Add new address successfully!";
  public static final String UPDATE_ADDRESS_SUCCESS = String.format(CHAR_SEQUENCE_3, UPDATE, ADDRESS, SUCCESS);
  public static final String UPDATE_ADDRESS_FAIL = String.format(CHAR_SEQUENCE_3, UPDATE, ADDRESS, FAIL);;
  public static final String DELETE_SUCCESS = String.format(CHAR_SEQUENCE_3, DELETE, ADDRESS, SUCCESS);
}
