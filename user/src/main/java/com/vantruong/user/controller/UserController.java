package com.vantruong.user.controller;

import com.vantruong.user.common.CommonResponse;
import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.constant.UserApiEndpoint;
import com.vantruong.user.dto.request.ChangePasswordRequest;
import com.vantruong.user.dto.request.CreateUserRequest;
import com.vantruong.user.dto.request.ResetPasswordRequest;
import com.vantruong.user.dto.request.UserPut;
import com.vantruong.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.user.constant.ApiEndpoint.REGISTER;
import static com.vantruong.user.constant.UserApiEndpoint.*;


@RestController
@RequestMapping( UserApiEndpoint.USER)
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping(REGISTER)
  public ResponseEntity<CommonResponse<Object>> createUser(@RequestBody @Valid CreateUserRequest userDto) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REGISTER_SUCCESS)
            .data(userService.createNewUser(userDto))
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

  @GetMapping(COUNT_USER)
  public ResponseEntity<CommonResponse<Object>> getUserCount() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(userService.getUserCount())
            .build());
  }
}
