package com.vantruong.gateway.service;

import com.vantruong.gateway.dto.request.IntrospectRequest;
import com.vantruong.gateway.common.CommonResponse;
import com.vantruong.gateway.dto.response.IntrospectResponse;
import com.vantruong.gateway.client.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
  IdentityClient identityClient;

  public Mono<CommonResponse<IntrospectResponse>> introspect(String token) {
    return identityClient.introspect(IntrospectRequest.builder()
            .token(token)
            .build());
  }
}
