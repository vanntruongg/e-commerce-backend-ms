package com.vantruong.product.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {
  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String CHAR_SEQUENCE_3 = "%s %s %s";

  //  actions
  public static final String FIND = "Find";

  public static final String CREATE = "Create";
  public static final String UPDATE = "Update";

  //  status
  public static final String SUCCESS = "successfully!";
  public static final String NOT_FOUND = "not found!";
  public static final String PRODUCT = "Product";
  public static final String CATEGORY = "Category";
  public static final String FIND_SUCCESS = String.format(CHAR_SEQUENCE_2, FIND, SUCCESS);
  public static final String PRODUCT_NOT_FOUND = String.format(CHAR_SEQUENCE_2, PRODUCT, NOT_FOUND);
  public static final String CATEGORY_NOT_FOUND = String.format(CHAR_SEQUENCE_2, CATEGORY, NOT_FOUND);
  public static final String CREATE_PRODUCT_SUCCESS = String.format(CHAR_SEQUENCE_3, CREATE, PRODUCT, SUCCESS);
  public static final String CREATE_CATEGORY_SUCCESS = String.format(CHAR_SEQUENCE_3, CREATE, CATEGORY, SUCCESS);
  public static final String UPDATE_CATEGORY_SUCCESS = String.format(CHAR_SEQUENCE_3, UPDATE, CATEGORY, SUCCESS);
  public static final String PRODUCT_CREATION_FAILED = "Failed to add the product!";
  public static final String UPDATE_SUCCESS = String.format(CHAR_SEQUENCE_2, UPDATE, SUCCESS);
}
