package com.vantruong.identity.service;

import com.nimbusds.jose.JOSEException;
import com.vantruong.identity.dto.response.IntrospectResponse;
import com.vantruong.identity.dto.request.IntrospectRequest;
import com.vantruong.identity.dto.request.LoginRequest;
import com.vantruong.identity.dto.request.LogoutRequest;
import com.vantruong.identity.dto.request.RefreshTokenRequest;
import com.vantruong.identity.dto.response.AuthenticationResponse;

import java.text.ParseException;

public interface AuthService {

  AuthenticationResponse login(LoginRequest request);


  AuthenticationResponse refreshToken(RefreshTokenRequest request);



  Boolean processVerifyEmail(String token);
  Boolean requestVerifyAccount(String email);
  IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;

  Boolean logout(LogoutRequest request) throws ParseException, JOSEException;
}
