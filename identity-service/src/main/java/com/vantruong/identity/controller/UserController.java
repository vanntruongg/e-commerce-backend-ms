package com.vantruong.identity.controller;

import com.vantruong.identity.common.CommonResponse;
import com.vantruong.identity.constant.UserApiEndpoint;
import com.vantruong.identity.dto.request.UserPut;
import com.vantruong.identity.dto.request.ChangePasswordRequest;
import com.vantruong.identity.dto.request.RegisterRequest;
import com.vantruong.identity.dto.request.ResetPasswordRequest;
import com.vantruong.identity.constant.MessageConstant;
import com.vantruong.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.identity.constant.AuthApiEndpoint.REGISTER;
import static com.vantruong.identity.constant.CommonApiEndpoint.IDENTITY;


@RestController
@RequestMapping(IDENTITY + UserApiEndpoint.USERS)
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping(REGISTER)
  public ResponseEntity<CommonResponse<Object>> createUser(@RequestBody RegisterRequest userDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REGISTER_SUCCESS)
            .data(userService.register(userDto))
            .build());
  }
  @GetMapping
  public ResponseEntity<CommonResponse<Object>> getAllUser(
          @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
          @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userService.getAllUser(pageNo, pageSize))
            .build());
  }

  @GetMapping(UserApiEndpoint.SEARCH_BY_NAME)
  public ResponseEntity<CommonResponse<Object>> searchByName(
          @PathVariable(name = "name") String name,
          @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo,
          @RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userService.searchByName(name, pageNo, pageSize))
            .build());
  }

  @GetMapping(UserApiEndpoint.USER_GET_BY_EMAIL)
  public ResponseEntity<CommonResponse<Object>> getUserByEmail(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userService.getUserByEmail(email))
            .build());
  }

  @GetMapping(UserApiEndpoint.PROFILE)
  public ResponseEntity<CommonResponse<Object>> getProfile() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userService.getUser())
            .build());
  }


  @PostMapping(UserApiEndpoint.UPDATE_USER)
  public ResponseEntity<CommonResponse<Object>> updateUser(@RequestBody UserPut userDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_USER_SUCCESS)
            .data(userService.updateUser(userDto))
            .build());
  }

  @PostMapping(UserApiEndpoint.UPDATE_PHONE)
  public ResponseEntity<CommonResponse<Object>> addPhoneNumber(@PathVariable("phone") String phone) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_USER_SUCCESS)
            .data(userService.addPhoneNumber(phone))
            .build());
  }

  @PostMapping(UserApiEndpoint.ACTIVE_ACCOUNT)
  public ResponseEntity<CommonResponse<Object>> activeAccount(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_USER_SUCCESS)
            .data(userService.activeAccount(email))
            .build());
  }

  @PostMapping(UserApiEndpoint.USER_CHANGE_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CHANGE_PASSWORD_SUCCESS)
            .data(userService.changePassword(changePasswordRequest))
            .build());
  }

  @DeleteMapping(UserApiEndpoint.DELETE_USER)
  public ResponseEntity<CommonResponse<Object>> deleteUser(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_USER_SUCCESS)
            .data(userService.deleteUser(email))
            .build());
  }

  @GetMapping(UserApiEndpoint.COUNT_USER)
  public ResponseEntity<CommonResponse<Object>> getUserCount() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userService.getUserCount())
            .build());
  }

  @PostMapping(UserApiEndpoint.FORGOT_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> requestForgotPassword(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REQUEST_RESET_PASSWORD_SUCCESS)
            .data(userService.requestForgotPassword(email))
            .build());
  }

  @PostMapping(UserApiEndpoint.RESET_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> resetPassword(@RequestBody ResetPasswordRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.RESET_PASSWORD_SUCCESS)
            .data(userService.resetPassword(request))
            .build());
  }
}
