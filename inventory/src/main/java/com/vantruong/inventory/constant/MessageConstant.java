package com.vantruong.inventory.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {
  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String CHAR_SEQUENCE_3 = "%s %s %s";
  public static final String CHAR_SEQUENCE_4 = "%s %s %s %s";
  public static final String CHAR_SEQUENCE_5 = "%s %s %s %s %s";
  public static final String SUCCESS = "thành công!";

//  actions
  public static final String FIND = "Tìm";
  public static final String UPDATE = "Cập nhật";

  //  status
  public static final String NOT_FOUND = "Không tìm thấy!";
  public static final String PRODUCT_NOT_FOUND = "Không tìm thấy sản phẩm!";


  public static final String FIND_SUCCESS = String.format(CHAR_SEQUENCE_2, FIND, SUCCESS);
  public static final String UPDATE_SUCCESS = String.format(CHAR_SEQUENCE_2, UPDATE, SUCCESS);;
}
