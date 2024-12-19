package com.vantruong.user.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  public String getUserId() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }
}
