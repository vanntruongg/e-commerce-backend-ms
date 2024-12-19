package com.vantruong.cart.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtils {
  public static String extractUserId() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }
}
