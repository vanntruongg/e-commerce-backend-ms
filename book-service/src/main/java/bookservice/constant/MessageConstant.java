package bookservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {
  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String CHAR_SEQUENCE_3 = "%s %s %s";

  //  actions
  public static final String FIND = "Tìm";

  public static final String CREATE = "Tạo";
  public static final String UPDATE = "Cập nhật";

  //  status
  public static final String SUCCESS = "Thành công!";
  public static final String NOT_FOUND = "không tìm thấy!";
  public static final String BOOK = "Sách";
  public static final String CATEGORY = "Danh mục";
  public static final String FIND_SUCCESS = String.format(CHAR_SEQUENCE_2, FIND, SUCCESS);
  public static final String BOOK_NOT_FOUND = String.format(CHAR_SEQUENCE_2, BOOK, NOT_FOUND);
  public static final String CATEGORY_NOT_FOUND = String.format(CHAR_SEQUENCE_2, CATEGORY, NOT_FOUND);
  public static final String CREATE_BOOK_SUCCESS = String.format(CHAR_SEQUENCE_3, CREATE, BOOK, SUCCESS);
  public static final String CREATE_CATEGORY_SUCCESS = String.format(CHAR_SEQUENCE_3, CREATE, CATEGORY, SUCCESS);
  public static final String INSUFFICIENT_PRODUCT_QUANTITY = "Số lượng sản phẩm trong kho không đủ!";
  public static final String UPDATE_QUANTITY_SUCCESS = "Cập nhật số lượng sản phẩm thành công.";
  public static final String UPDATE_SUCCESS = String.format(CHAR_SEQUENCE_2, UPDATE, SUCCESS);
}
