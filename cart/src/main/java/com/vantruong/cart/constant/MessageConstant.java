package com.vantruong.cart.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {
  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String SUCCESS = "thành công!";

//  actions
  public static final String FIND = "Tìm";
  public static final String UPDATE = "Cập nhật";
  public static final String DELETE = "Xóa";
  public static final String ADD_TO_CART = "Thêm vào giỏ hàng";

//  status
  public static final String NOT_FOUND = "Không tìm thấy";
  public static final String USER = "người dùng";

  public static final String FIND_SUCCESS = String.format(CHAR_SEQUENCE_2, FIND, SUCCESS);
  public static final String UPDATE_SUCCESS = String.format(CHAR_SEQUENCE_2, UPDATE, SUCCESS);
  public static final String ADD_TO_CART_SUCCESS = String.format(CHAR_SEQUENCE_2, ADD_TO_CART, SUCCESS);
  public static final String DELETE_SUCCESS = "Xóa sản phẩm khỏi giỏ hàng thành công";
  public static final String USER_NOT_FOUND = String.format(CHAR_SEQUENCE_2, NOT_FOUND, USER);
  public static final String CART_NOT_FOUND = "Không tìm thấy giỏ hàng!";
  public static final String INSUFFICIENT_PRODUCT_QUANTITY = "Số lượng trong kho không đủ!";
}
