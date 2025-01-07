package com.vantruong.gateway.client;


import com.vantruong.gateway.dto.request.IntrospectRequest;
import com.vantruong.gateway.common.CommonResponse;
import com.vantruong.gateway.dto.response.IntrospectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

import static com.vantruong.gateway.constant.InternalApiEndpoint.*;

public interface IdentityClient {
  @PostExchange(url = INTROSPECT_TOKEN, contentType = MediaType.APPLICATION_JSON_VALUE)
  Mono<CommonResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
