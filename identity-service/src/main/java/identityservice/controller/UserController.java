package identityservice.controller;

import identityservice.common.CommonResponse;
import identityservice.constant.MessageConstant;
import identityservice.dto.UserDto;
import identityservice.dto.request.ChangePasswordRequest;
import identityservice.dto.request.RegisterRequest;
import identityservice.dto.request.ResetPasswordRequest;
import identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static identityservice.constant.AuthApiEndpoint.REGISTER;
import static identityservice.constant.CommonApiEndpoint.IDENTITY;
import static identityservice.constant.UserApiEndpoint.*;


@RestController
@RequestMapping(IDENTITY + USERS)
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  @PostMapping(REGISTER)
  public ResponseEntity<CommonResponse<Object>> addNewUser(@RequestBody RegisterRequest userDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REGISTER_SUCCESS)
            .data(userService.register(userDto))
            .build());
  }
  @GetMapping
  public ResponseEntity<CommonResponse<Object>> getAllUser() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userService.getAllUser())
            .build());
  }

  @GetMapping(USER_GET_BY_EMAIL)
  public ResponseEntity<CommonResponse<Object>> getUserByEmail(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userService.getUserByEmail(email))
            .build());
  }

  @GetMapping(PROFILE)
  public ResponseEntity<CommonResponse<Object>> getProfile() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userService.getProfile())
            .build());
  }


  @PostMapping(UPDATE_USER)
  public ResponseEntity<CommonResponse<Object>> updateUser(@RequestBody UserDto userDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_USER_SUCCESS)
            .data(userService.updateUser(userDto))
            .build());
  }

  @PostMapping(UPDATE_PHONE)
  public ResponseEntity<CommonResponse<Object>> addPhoneNumber(@PathVariable("phone") String phone) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_USER_SUCCESS)
            .data(userService.addPhoneNumber(phone))
            .build());
  }

  @PostMapping(USER_CHANGE_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CHANGE_PASSWORD_SUCCESS)
            .data(userService.changePassword(changePasswordRequest))
            .build());
  }

  @DeleteMapping(DELETE_USER)
  public ResponseEntity<CommonResponse<Object>> deleteUser(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_USER_SUCCESS)
            .data(userService.deleteUser(email))
            .build());
  }

  @GetMapping(COUNT_USER)
  public ResponseEntity<CommonResponse<Object>> getUserCount() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userService.getUserCount())
            .build());
  }

  @PostMapping(FORGOT_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> requestForgotPassword(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REQUEST_RESET_PASSWORD_SUCCESS)
            .data(userService.requestForgotPassword(email))
            .build());
  }

  @PostMapping(RESET_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> resetPassword(@RequestBody ResetPasswordRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.RESET_PASSWORD_SUCCESS)
            .data(userService.resetPassword(request))
            .build());
  }
}
