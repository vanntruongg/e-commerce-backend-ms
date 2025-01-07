package com.vantruong.user.helper;

import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.exception.AccessDeniedException;
import com.vantruong.user.exception.ErrorCode;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component(value = "securityContextHelper")
public class SecurityContextHelper {

  public String getUserId() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth instanceof AnonymousAuthenticationToken) {
      throw new AccessDeniedException(ErrorCode.DENIED, MessageConstant.ACCESS_DENIED);
    }

    JwtAuthenticationToken contextHolder = (JwtAuthenticationToken) auth;
    return contextHolder.getToken().getClaim("email");
  }

  public Set<String> getRoles() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth instanceof AnonymousAuthenticationToken) {
      throw new AccessDeniedException(ErrorCode.DENIED, MessageConstant.ACCESS_DENIED);
    }

    JwtAuthenticationToken contextHolder = (JwtAuthenticationToken) auth;
    return contextHolder.getToken().getClaim("roles");
  }

}
