package com.vantruong.identity.controller;

import com.nimbusds.jose.JOSEException;
import com.vantruong.identity.common.CommonResponse;
import com.vantruong.identity.constant.MessageConstant;
import com.vantruong.identity.dto.request.IntrospectRequest;
import com.vantruong.identity.service.IdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

import static com.vantruong.identity.constant.ApiEndpoint.IDENTITY;
import static com.vantruong.identity.constant.InternalApiEndpoint.INTERNAL;
import static com.vantruong.identity.constant.InternalApiEndpoint.INTROSPECT_TOKEN;

@RestController
@RequestMapping(IDENTITY + INTERNAL)
@RequiredArgsConstructor
public class InternalIdentityController {
  private final IdentityService identityService;

  @PostMapping(INTROSPECT_TOKEN)
  public ResponseEntity<CommonResponse<Object>> introspectToken(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
    return ResponseEntity.ok().body(CommonResponse.builder()
                    .isSuccess(true)
                    .message(MessageConstant.SUCCESS)
                    .data(identityService.introspect(request))
            .build());
  }
}
