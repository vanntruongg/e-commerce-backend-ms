package com.vantruong.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

  public List<String> openApiEndpoints = List.of(
//          Identity endpoints
          "/api/v1/identity/users/register",
          "/api/v1/identity/auth/login",
          "/api/v1/identity/auth/logout",
          "/api/v1/identity/auth/refresh-token",
          "/api/v1/identity/auth/verify-email",
          "/api/v1/identity/auth/request/verify",
          "/api/v1/identity/users/forgot-password",
          "/api/v1/identity/users/reset-password",
//          product endpoints
          "/api/v1/product",
          "/api/v1/product/category",
          "/api/v1/product/get/.*",
          "/api/v1/product/category/get/.*",

//          other endpoints
          "/api/v1/eureka",

//          address
          "/api/v1/address/user/create",
          //          address
          "/api/v1/inventory/.*",
//          payment
          "/api/v1/order-orchestrator/vnpay-callback"
  );

  public boolean isPublicEndpoint(ServerHttpRequest request) {
    return openApiEndpoints.stream()
            .anyMatch(publicEndpoint -> request.getURI().getPath().matches(publicEndpoint));
  }
}
