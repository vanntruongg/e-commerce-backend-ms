package com.vantruong.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vantruong.gateway.common.CommonResponse;
import com.vantruong.gateway.service.IdentityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
  IdentityService identityService;
  ObjectMapper objectMapper;
  RouteValidator routeValidator;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    if (routeValidator.isPublicEndpoint(exchange.getRequest()))
      return chain.filter(exchange);

//  get token from authentication header
    List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
    if (CollectionUtils.isEmpty(authHeader)) {
      return unAuthenticated(exchange.getResponse());
    }
    String token = authHeader.get(0).replace("Bearer ", "");

    return identityService.introspect(token).flatMap(introspectResponseCommonResponse ->
            introspectResponseCommonResponse.getData().isValid()
                    ? chain.filter(exchange)
                    : unAuthenticated(exchange.getResponse())
    ).onErrorResume(throwable -> unAuthenticated(exchange.getResponse()));
  }

  @Override
  public int getOrder() {
    return -1;
  }

  private Mono<Void> unAuthenticated(ServerHttpResponse response) {
    CommonResponse<?> commonResponse = CommonResponse.builder()
            .isSuccess(false)
            .message("Unauthenticated")
            .build();

    String body;
    try {
      body = objectMapper.writeValueAsString(commonResponse);
    } catch (JsonProcessingException ex) {
      throw new RuntimeException(ex);
    }
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

    return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
  }
}
