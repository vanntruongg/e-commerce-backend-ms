package identityservice.service;

import com.nimbusds.jose.JOSEException;
import identityservice.dto.request.IntrospectRequest;
import identityservice.dto.request.LoginRequest;
import identityservice.dto.request.LogoutRequest;
import identityservice.dto.request.RefreshTokenRequest;
import identityservice.dto.response.IntrospectResponse;
import identityservice.dto.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.text.ParseException;

public interface AuthService {

  AuthenticationResponse login(LoginRequest request);


  AuthenticationResponse refreshToken(RefreshTokenRequest request);



  Boolean processVerifyEmail(String token);
  Boolean requestVerifyAccount(String email);
  IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;

  Boolean logout(LogoutRequest request) throws ParseException, JOSEException;
}
