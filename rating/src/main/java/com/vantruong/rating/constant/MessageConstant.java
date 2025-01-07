package com.vantruong.rating.constant;

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
  public static final String DELETE_RATING = "Xóa đánh giá";
  public static final String FIND_SUCCESS = String.format(CHAR_SEQUENCE_2, FIND, SUCCESS);
  public static final String DELETE_SUCCESS = String.format(CHAR_SEQUENCE_2, DELETE_RATING, SUCCESS);

//  status
  public static final String NOT_FOUND = "Không tìm thấy!";
  public static final String RATING_NOT_FOUND = "Không tìm thấy đánh giá sản phẩm!";

  public static final String CREATE_RATING_SUCCESS = "Đánh giá sản phẩm thành công";
  public static final String UPVOTE_RATING_SUCCESS = "Đã thích đánh giá";

  public static final String ACCESS_DENIED = "You are not allowed to perform this action.";
  public static final String RATING_ALREADY_EXISTED = "Bạn đã đánh giá sản phẩm này";
}
