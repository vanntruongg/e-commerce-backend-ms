package identityservice.service;

import identityservice.dto.request.IntrospectRequest;
import identityservice.dto.request.LoginRequest;
import identityservice.dto.request.RefreshTokenRequest;
import identityservice.dto.request.RegisterRequest;
import identityservice.dto.response.IntrospectResponse;
import identityservice.dto.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

  LoginResponse login(LoginRequest request);


  LoginResponse refreshToken(RefreshTokenRequest request);


  Boolean logout(HttpServletRequest request, HttpServletResponse response);

  Boolean processVerifyEmail(String token);
  Boolean requestVerifyAccount(String email);
  IntrospectResponse introspect(IntrospectRequest request);
}
