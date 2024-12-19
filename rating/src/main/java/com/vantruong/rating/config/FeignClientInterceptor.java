package com.vantruong.rating.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
  @Override
  public void apply(RequestTemplate requestTemplate) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth instanceof JwtAuthenticationToken jwtAuthToken) {
      String token = jwtAuthToken.getToken().getTokenValue();
      requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
  }
}
