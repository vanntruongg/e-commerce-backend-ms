package com.vantruong.identity.service;

import com.vantruong.identity.dto.UserDto;
import com.vantruong.identity.dto.request.ChangePasswordRequest;
import com.vantruong.identity.dto.request.RegisterRequest;
import com.vantruong.identity.dto.request.ResetPasswordRequest;
import identityservice.dto.request.*;
import com.vantruong.identity.entity.User;

import java.util.List;

public interface UserService {

  Boolean register(RegisterRequest userDto);

  List<User> getAllUser();

  User getUserByEmail(String email);
  User findUserByEmail(String email);

  User updateUser(UserDto userDto);

  Boolean changePassword(ChangePasswordRequest changePasswordRequest);

  Boolean deleteUser(String email);

  User getProfile();

  Boolean addPhoneNumber(String phone);

  Long getUserCount();


  Boolean requestForgotPassword(String email);

  Boolean resetPassword(ResetPasswordRequest request);

  User existedByEmail(String email);

}
