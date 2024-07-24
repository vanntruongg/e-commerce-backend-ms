package com.vantruong.identity.security;

import com.vantruong.identity.entity.Role;
import com.vantruong.identity.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component(value = "securityContextHelper")
@RequiredArgsConstructor
public class SecurityContextHelper {
  private final UserDetailsServiceImpl userDetailsService;

  public UserDetailsImpl getUserDetails() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
      Jwt jwt = (Jwt) authentication.getCredentials();
      String email = jwt.getClaimAsString("email");
      return userDetailsService.loadUserByUsername(email);
    }
    return null;
  }

  public String getUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null) {
      Jwt jwt = (Jwt) authentication.getCredentials();
      return jwt.getClaimAsString("email");
    }
    throw new AuthenticationException();
  }

  public Set<Role> getRoles() {
    UserDetailsImpl userDetails = getUserDetails();
    if (userDetails != null) {
      return userDetails.getRole();
    }
    throw new AuthenticationException();
  }

}
