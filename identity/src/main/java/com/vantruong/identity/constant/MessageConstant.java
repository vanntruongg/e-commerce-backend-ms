package com.vantruong.identity.constant;

import com.vantruong.identity.entity.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class MessageConstant {

  public static final String CHAR_SEQUENCE_2 = "%s %s";
  public static final String CHAR_SEQUENCE_3 = "%s %s %s";

  public static final String SUCCESS = "successfully!";
  public static final String NOT_FOUND = "not found!";
  public static final String FAILED = "failed!";
  public static final String LOGIN = "Login";
  private static final String LOGOUT = "Logout";
  private static final String REGISTER = "Register";
  private static final String VERIFY = "Verify";
  private static final String UPDATE = "Update";
  private static final String DELETE = "Delete";
  public static final String REFRESH_TOKEN = "Refresh token";
  public static final String CHANGE_PASSWORD = "Change password";

  public static final String USER = "User";
  public static final String ROLE = "Role";


  public static final String ACCOUNT_UNAVAILABLE = "Account is unavailable";
  public static final String UNVERIFIED_ACCOUNT = "Account not verified!";
  public static final String PASSWORD_INCORRECT = "Incorrect password";
  public static final String EMAIL_EXISTED = "Email is existed!";
  public static final String REQUEST_RESET_PASSWORD_SUCCESS = "Password request was successfully!";
  public static final String RESET_PASSWORD_SUCCESS = "Reset password successfully!";
  public static final String REQUEST_VERIFY_SUCCESS = "Account verification request was successful!";
  public static final String ACCESS_DENIED = "You are not allowed to perform this action.";
  public static final String EXPIRED_TOKEN = "The verification code has expired. Please request a new one.";
  public static final String INVALID_TOKEN = "The verification code is invalid or has expired.";
  public static final String OLD_PASSWORD_NOT_MATCHES = "The old password is incorrect!";

  public static final String ROLE_NOT_FOUND = String.format(CHAR_SEQUENCE_2, ROLE, NOT_FOUND);
  public static final String USER_NOT_FOUND = String.format(CHAR_SEQUENCE_2, USER, NOT_FOUND);
  public static final String UPDATE_USER_SUCCESS = String.format(CHAR_SEQUENCE_3, UPDATE, USER, SUCCESS);
  public static final String VERIFY_SUCCESS = String.format(CHAR_SEQUENCE_2, VERIFY, SUCCESS);
  public static final String REGISTER_SUCCESS = String.format(CHAR_SEQUENCE_2, REGISTER, SUCCESS);
  public static final String CHANGE_PASSWORD_SUCCESS = String.format(CHAR_SEQUENCE_2, CHANGE_PASSWORD, SUCCESS);
  public static final String DELETE_USER_SUCCESS = String.format(CHAR_SEQUENCE_3, DELETE, USER, SUCCESS);
  public static final String LOGIN_SUCCESS = String.format(CHAR_SEQUENCE_2, LOGIN, SUCCESS);
  public static final String REFRESH_TOKEN_FAIL = String.format(CHAR_SEQUENCE_2, REFRESH_TOKEN, FAILED);
  public static final String LOGOUT_SUCCESS = String.format(CHAR_SEQUENCE_2, LOGOUT, SUCCESS);


}
