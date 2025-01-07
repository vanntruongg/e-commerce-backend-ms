package com.vantruong.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RouteValidator {

  public List<String> openApiEndpoints = List.of(
//          Identity endpoints
          "/api/v1/identity/login",
          "/api/v1/identity/logout",
          "/api/v1/identity/refresh-token",

          "/api/v1/user/register",
          "/api/v1/user/verify-email",
          "/api/v1/user/request/verify",
          "/api/v1/user/forgot-password",
          "/api/v1/user/reset-password",
//          product endpoints
          "/api/v1/product",
          "/api/v1/product/category",
          "/api/v1/product/get/.*",
          "/api/v1/product/category/get/.*",

//          other endpoints
          "/api/v1/eureka",

//          address
//          "/api/v1/address/user/create",
//          inventory
          "/api/v1/inventory/.*",
//          payment
          "/api/v1/order-orchestrator/vnpay-callback",
//          rating
          "/api/v1/ratings/get/.*"
  );

  public boolean isPublicEndpoint(ServerHttpRequest request) {
    return openApiEndpoints.stream()
            .anyMatch(publicEndpoint -> request.getURI().getPath().matches(publicEndpoint));
  }
}
