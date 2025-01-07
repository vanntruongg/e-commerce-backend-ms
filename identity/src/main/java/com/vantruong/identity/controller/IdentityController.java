package com.vantruong.identity.controller;

import com.nimbusds.jose.JOSEException;
import com.vantruong.identity.common.CommonResponse;
import com.vantruong.identity.constant.MessageConstant;
import com.vantruong.identity.dto.request.*;
import com.vantruong.identity.service.IdentityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static com.vantruong.identity.constant.ApiEndpoint.*;


@RestController
@RequestMapping(IDENTITY)
@RequiredArgsConstructor
public class IdentityController {
  private final IdentityService identityService;

  @PostMapping(REGISTER)
  public ResponseEntity<CommonResponse<Object>> createUser(@RequestBody @Valid RegisterRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REGISTER_SUCCESS)
            .data(identityService.register(request))
            .build());
  }
  @PostMapping(LOGIN)
  public ResponseEntity<CommonResponse<Object>> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.LOGIN_SUCCESS)
            .data(identityService.login(request))
            .build());
  }

  @PostMapping(REFRESH_TOKEN)
  public ResponseEntity<CommonResponse<Object>> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
    try {
      return ResponseEntity.ok().body(CommonResponse.builder()
              .isSuccess(true).data(identityService.refreshToken(request)).build());
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
              .isSuccess(false).message(MessageConstant.REFRESH_TOKEN_FAIL).build());
    }
  }

  @PostMapping(LOGOUT)
  public ResponseEntity<CommonResponse<Object>> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .data(identityService.logout(request))
            .message(MessageConstant.LOGOUT_SUCCESS)
            .build());

  }

  @PostMapping(ACTIVE_ACCOUNT)
  public ResponseEntity<CommonResponse<Object>> activeAccount(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.UPDATE_USER_SUCCESS)
            .data(identityService.activeAccount(email))
            .build());
  }

  @PostMapping(CHANGE_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.CHANGE_PASSWORD_SUCCESS)
            .data(identityService.changePassword(changePasswordRequest))
            .build());
  }

  @DeleteMapping(DELETE_USER)
  public ResponseEntity<CommonResponse<Object>> deleteUser(@PathVariable("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_USER_SUCCESS)
            .data(identityService.deleteUser(email))
            .build());
  }

  @PostMapping(VERIFY_EMAIL)
  public ResponseEntity<CommonResponse<Object>> verifyEmail(@RequestParam(value = "token") String token) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.VERIFY_SUCCESS)
            .data(identityService.processVerifyEmail(token))
            .build());
  }

  @PostMapping(REQUEST_VERIFY)
  public ResponseEntity<CommonResponse<Object>> requestVerifyAccount(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REQUEST_VERIFY_SUCCESS)
            .data(identityService.requestVerifyAccount(email))
            .build());
  }

  @PostMapping(FORGOT_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> requestForgotPassword(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REQUEST_RESET_PASSWORD_SUCCESS)
            .data(identityService.requestForgotPassword(email))
            .build());
  }

  @PostMapping(RESET_PASSWORD)
  public ResponseEntity<CommonResponse<Object>> resetPassword(@RequestBody ResetPasswordRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.RESET_PASSWORD_SUCCESS)
            .data(identityService.resetPassword(request))
            .build());
  }
}
