package identityservice.service;

import identityservice.dto.UserDto;
import identityservice.dto.request.*;
import identityservice.entity.User;

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

  Boolean addAddress(String address);

  Long getUserCount();


  Boolean requestForgotPassword(String email);

  Boolean resetPassword(ResetPasswordRequest request);

  User existedByEmail(String email);

}
