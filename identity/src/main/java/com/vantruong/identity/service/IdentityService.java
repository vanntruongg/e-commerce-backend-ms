package com.vantruong.identity.service;

import com.nimbusds.jose.JOSEException;
import com.vantruong.common.exception.Constant;
import com.vantruong.identity.constant.MessageConstant;
import com.vantruong.identity.dto.request.*;
import com.vantruong.identity.dto.response.AuthenticationResponse;
import com.vantruong.identity.dto.response.IntrospectResponse;
import com.vantruong.identity.entity.InvalidatedToken;
import com.vantruong.identity.exception.*;
import com.vantruong.identity.repository.InvalidatedTokenRepository;
import com.vantruong.identity.security.JwtService;
import com.vantruong.identity.security.UserDetailsImpl;
import com.vantruong.identity.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsServiceImpl userDetailsService;
  private final InvalidatedTokenRepository invalidatedTokenRepository;

  public AuthenticationResponse login(LoginRequest request) {
    var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    // check if user has verified the account
    if (!userDetails.isEnabled()) {
      throw new UnVerifiedAccountException(Constant.ErrorCode.DENIED, MessageConstant.UNVERIFIED_ACCOUNT, new Throwable("unVerify"));
    }

    if (!userDetails.isActive()) {
      throw new AccountUnAvailableException(Constant.ErrorCode.DENIED, MessageConstant.ACCOUNT_UNAVAILABLE, new Throwable("unAvailable"));
    }

    String accessToken = jwtService.generateToken(userDetails, true);
    String refreshToken = jwtService.generateToken(userDetails, false);
    return AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }

  public IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException {
    var token = request.getToken();
    boolean isValid = true;
    try {
      jwtService.verifyToken(token);
    } catch (AuthenticationException e) {
      isValid = false;
    }
    return IntrospectResponse.builder()
            .valid(isValid)
            .build();
  }

  public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
    String token = request.getRefreshToken();

    if (!jwtService.validateToken(token)) {
      throw new BadCredentialsException("Invalid refresh token!");
    }
    String email = jwtService.getEmailFromToken(token);
    UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(email);
    String accessToken = jwtService.generateToken(userDetails, true);

    return AuthenticationResponse.builder()
            .accessToken(accessToken)
            .build();
  }

  public Boolean logout(LogoutRequest request) throws ParseException, JOSEException {
    var signToken = jwtService.verifyToken(request.getToken());

    String jwt = signToken.getJWTClaimsSet().getJWTID();
    Date expiredTime = signToken.getJWTClaimsSet().getExpirationTime();

    InvalidatedToken invalidatedToken = InvalidatedToken.builder()
            .id(jwt)
            .expiredTime(expiredTime)
            .build();
    invalidatedTokenRepository.save(invalidatedToken);

    SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
    SecurityContextHolder.getContext().setAuthentication(null);

    return true;
  }
}
