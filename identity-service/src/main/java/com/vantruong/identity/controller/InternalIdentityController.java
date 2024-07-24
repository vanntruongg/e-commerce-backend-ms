package com.vantruong.identity.controller;

import com.nimbusds.jose.JOSEException;
import com.vantruong.identity.common.CommonResponse;
import com.vantruong.identity.dto.request.IntrospectRequest;
import com.vantruong.identity.service.AuthService;
import com.vantruong.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static com.vantruong.identity.constant.AuthApiEndpoint.IDENTITY;
import static com.vantruong.identity.constant.InternalApiEndpoint.*;


@RestController
@RequestMapping(INTERNAL + IDENTITY)
@RequiredArgsConstructor
public class InternalIdentityController {

  private final AuthService identityService;
  private final UserService userService;

  @PostMapping(INTROSPECT)
  public ResponseEntity<CommonResponse<Object>> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(identityService.introspect(request).isValid())
            .data(identityService.introspect(request))
            .build());
  }

  @GetMapping(USER_EXISTED_BY_EMAIL)
  public ResponseEntity<CommonResponse<Object>> existedByEmail(@RequestParam("email") String email) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .data(userService.existedByEmail(email))
            .build());
  }
}
