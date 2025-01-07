package com.vantruong.order.util;

import com.vantruong.order.constant.MessageConstant;
import com.vantruong.order.exception.AccessDeniedException;
import com.vantruong.order.exception.ErrorCode;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtils {

  public static String extractUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth instanceof AnonymousAuthenticationToken) {
      throw new AccessDeniedException(ErrorCode.DENIED, MessageConstant.ACCESS_DENIED);
    }

    JwtAuthenticationToken contextHolder = (JwtAuthenticationToken) auth;
    return contextHolder.getToken().getSubject();
  }
}
