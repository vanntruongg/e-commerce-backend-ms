package com.vantruong.identity.service;

import com.nimbusds.jose.JOSEException;
import com.vantruong.identity.client.NotificationClient;
import com.vantruong.identity.common.Utils;
import com.vantruong.identity.constant.MessageConstant;
import com.vantruong.identity.dto.request.*;
import com.vantruong.identity.dto.response.AuthenticationResponse;
import com.vantruong.identity.dto.response.IntrospectResponse;
import com.vantruong.identity.entity.InvalidatedToken;
import com.vantruong.identity.entity.Role;
import com.vantruong.identity.entity.Token;
import com.vantruong.identity.entity.User;
import com.vantruong.identity.enums.AccountStatus;
import com.vantruong.identity.enums.ERole;
import com.vantruong.identity.enums.TokenType;
import com.vantruong.identity.exception.*;
import com.vantruong.identity.repository.InvalidatedTokenRepository;
import com.vantruong.identity.repository.UserRepository;
import com.vantruong.identity.security.JwtService;
import com.vantruong.identity.security.SecurityContextHelper;
import com.vantruong.identity.security.UserDetailsImpl;
import com.vantruong.identity.security.UserDetailsServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
  JwtService jwtService;
  UserDetailsServiceImpl userDetailsService;
  RoleService roleService;
  UserRepository userRepository;
  AuthenticationManager authenticationManager;
  InvalidatedTokenRepository invalidatedTokenRepository;
  PasswordEncoder passwordEncoder;
  SecurityContextHelper securityContextHelper;
  NotificationClient notificationClient;
  TokenService tokenService;


  private User findByEmail(String email) {
    return userRepository.findById(email)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.USER_NOT_FOUND));
  }

  @Transactional
  public Boolean register(RegisterRequest request) {
    if (userRepository.existsById(request.getEmail())) {
      throw new FormException(ErrorCode.FORM_ERROR, MessageConstant.EMAIL_EXISTED, new Throwable("email"));
    }
    Role role = roleService.findByRole(ERole.USER);
    User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .roles(Collections.singleton(role))
            .build();
    userRepository.save(user);
    return true;
  }

  public AuthenticationResponse login(LoginRequest request) {
    var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    // check if user has verified the account
    if (!userDetails.isActive()) {
      throw new UnVerifiedAccountException(ErrorCode.DENIED, MessageConstant.UNVERIFIED_ACCOUNT, new Throwable("unVerify"));
    }

    if (userDetails.isEnabled()) {
      throw new AccountUnAvailableException(ErrorCode.DENIED, MessageConstant.ACCOUNT_UNAVAILABLE, new Throwable("unAvailable"));
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

  public Boolean processVerifyEmail(String tokenValue) {
    Token token = tokenService.findByTokenValue(tokenValue);
    User user = token.getUser();

    if (TokenType.VERIFICATION.getTokenType().equals(token.getTokenType())) {
      if (LocalDateTime.now().isBefore(token.getExpiredDate())) {
        user.setStatus(AccountStatus.ACTIVE);
      } else {
        throw new ExpiredException(ErrorCode.EXPIRED, MessageConstant.EXPIRED_TOKEN);
      }
    } else {
      throw new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.INVALID_TOKEN);
    }
    userRepository.save(user);
    return true;
  }

  public Boolean requestVerifyAccount(String email) {
    Token token = Utils.generateToken(TokenType.VERIFICATION);
    User user = findByEmail(email);
    token.setUser(user);

    SendMailVerifyUserRequest request = SendMailVerifyUserRequest.builder()
            .email(user.getEmail())
            .token(token.getTokenValue())
            .build();
    tokenService.createToken(token);
//    notificationClient.sendVerificationEmail(request);
    return true;
  }

  public Boolean requestForgotPassword(String email) {
    Token token = Utils.generateToken(TokenType.RESET_PASSWORD);
    User user = findByEmail(email);
    token.setUser(user);
    tokenService.createToken(token);
    SendMailVerifyUserRequest request = SendMailVerifyUserRequest.builder()
            .email(user.getEmail())
            .token(token.getTokenValue())
            .build();
//    notificationClient.sendForgotPassword(request);
    return true;
  }

  public Boolean resetPassword(ResetPasswordRequest request) {
    Token token = tokenService.findByTokenValue(request.getToken());
    User user = token.getUser();
    if (TokenType.RESET_PASSWORD.getTokenType().equals(token.getTokenType())) {
      if (LocalDateTime.now().isBefore(token.getExpiredDate())) {
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
      } else {
        throw new ExpiredException(ErrorCode.EXPIRED, MessageConstant.EXPIRED_TOKEN);
      }
    } else {
      throw new NotFoundException(ErrorCode.DENIED, MessageConstant.INVALID_TOKEN);
    }
    userRepository.save(user);
    return true;
  }

  @Transactional
  public Boolean changePassword(ChangePasswordRequest changePasswordRequest) {
    String email = securityContextHelper.getUserId();
    User user = findByEmail(email);

    if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
      user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
      userRepository.save(user);
      return true;
    } else {
      throw new FormException(ErrorCode.DENIED, MessageConstant.OLD_PASSWORD_NOT_MATCHES, new Throwable("oldPassword"));
    }
  }

  public boolean activeAccount(String email) {
    User user = findByEmail(email);
    user.setStatus(AccountStatus.ACTIVE);
    userRepository.save(user);
    return true;
  }

  public Boolean deleteUser(String email) {
    User user = findByEmail(email);
    user.setStatus(AccountStatus.DELETED);
    userRepository.save(user);
    return true;
  }

}
