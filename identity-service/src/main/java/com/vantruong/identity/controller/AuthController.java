package com.vantruong.identity.controller;

import com.nimbusds.jose.JOSEException;
import com.vantruong.identity.common.CommonResponse;
import com.vantruong.identity.constant.UserApiEndpoint;
import com.vantruong.identity.dto.request.LoginRequest;
import com.vantruong.identity.dto.request.RefreshTokenRequest;
import com.vantruong.identity.service.AuthService;
import com.vantruong.identity.constant.MessageConstant;
import com.vantruong.identity.dto.request.LogoutRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static com.vantruong.identity.constant.AuthApiEndpoint.*;


@RestController
@RequestMapping(IDENTITY + AUTH)
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping(LOGIN)
  public ResponseEntity<CommonResponse<Object>> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.LOGIN_SUCCESS)
            .data(authService.login(request))
            .build());
  }

  @PostMapping(REFRESH_TOKEN)
  public ResponseEntity<CommonResponse<Object>> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
    try {
      return ResponseEntity.ok().body(CommonResponse.builder()
              .isSuccess(true).data(authService.refreshToken(request)).build());
    } catch (BadCredentialsException ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponse.builder()
              .isSuccess(false).message(MessageConstant.REFRESH_TOKEN_FAIL).build());
    }
  }

  @PostMapping(UserApiEndpoint.VERIFY_EMAIL)
  public ResponseEntity<CommonResponse<Object>> verifyEmail(@RequestParam(value = "token") String token) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.VERIFY_SUCCESS)
            .data(authService.processVerifyEmail(token))
            .build());
  }

  @PostMapping(UserApiEndpoint.REQUEST_VERIFY)
  public ResponseEntity<CommonResponse<Object>> requestVerifyAccount(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.REQUEST_VERIFY_SUCCESS)
            .data(authService.requestVerifyAccount(email))
            .build());
  }

  @PostMapping(LOGOUT)
  public ResponseEntity<CommonResponse<Object>> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {

    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .data(authService.logout(request))
            .message(MessageConstant.LOGOUT_SUCCESS)
            .build());

  }
}
