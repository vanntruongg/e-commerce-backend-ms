package com.vantruong.address.constant;

import jakarta.ws.rs.DELETE;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {

  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String CHAR_SEQUENCE_3 = "%s %s %s";
  public static final String SUCCESS = "thành công!";
  public static final String FAIL = "fail!";

  public static final String FIND_SUCCESS = SUCCESS;
  public static final String ADDRESS = "địa chỉ";
  public static final String UPDATE = "Cập nhật";
  public static final String DELETE = "Xóa";
  public static final String NOT_FOUND = "Không tìm thấy!";
  public static final String CREATE_ADDRESS_SUCCESS = "Thêm địa chỉ mới thành công!";
  public static final String UPDATE_ADDRESS_SUCCESS = String.format(CHAR_SEQUENCE_3, UPDATE, ADDRESS, SUCCESS);
  public static final String UPDATE_ADDRESS_FAIL = String.format(CHAR_SEQUENCE_3, UPDATE, ADDRESS, FAIL);;
  public static final String DELETE_SUCCESS = String.format(CHAR_SEQUENCE_3, DELETE, ADDRESS, SUCCESS);
}
