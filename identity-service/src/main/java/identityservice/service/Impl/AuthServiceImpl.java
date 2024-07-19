package identityservice.service.Impl;

import com.nimbusds.jose.JOSEException;
import identityservice.common.Utils;
import identityservice.constant.MessageConstant;
import identityservice.dto.request.*;
import identityservice.dto.response.AuthenticationResponse;
import identityservice.dto.response.IntrospectResponse;
import identityservice.entity.InvalidatedToken;
import identityservice.entity.Token;
import identityservice.entity.User;
import identityservice.enums.AccountStatus;
import identityservice.enums.TokenType;
import identityservice.exception.*;
import identityservice.repository.InvalidatedTokenRepository;
import identityservice.repository.UserRepository;
import identityservice.repository.client.MailClient;
import identityservice.security.JwtService;
import identityservice.security.SecurityContextHelper;
import identityservice.security.UserDetailsImpl;
import identityservice.security.UserDetailsServiceImpl;
import identityservice.service.AuthService;
import identityservice.service.TokenService;
import identityservice.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
  JwtService jwtService;
  AuthenticationManager authenticationManager;
  UserService userService;
  UserDetailsServiceImpl userDetailsService;
  TokenService tokenService;
  UserRepository userRepository;
  MailClient mailClient;
  InvalidatedTokenRepository invalidatedTokenRepository;

  @Override
  public AuthenticationResponse login(LoginRequest request) {
    var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    // check if user has verified the account
    if (!userDetails.isEnabled()) {
      throw new UnVerifiedAccountException(ErrorCode.DENIED, MessageConstant.UNVERIFIED_ACCOUNT, new Throwable("unVerify"));
    }

    if (!userDetails.isActive()) {
      throw new AccountUnAvailableException(ErrorCode.DENIED, MessageConstant.ACCOUNT_UNAVAILABLE, new Throwable("unAvailable"));
    }

    String accessToken = jwtService.generateToken(userDetails, true);
    String refreshToken = jwtService.generateToken(userDetails, false);
    return AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }

  @Override
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

  @Override
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

  @Override
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

  @Override
  public Boolean requestVerifyAccount(String email) {
    Token token = Utils.generateToken(TokenType.VERIFICATION);
    User user = userService.findUserByEmail(email);
    token.setUser(user);

    SendMailVerifyUserRequest request = SendMailVerifyUserRequest.builder()
            .email(user.getEmail())
            .name(user.getFirstName())
            .token(token.getTokenValue())
            .build();
    mailClient.sendVerificationEmail(request);
    tokenService.createToken(token);
    return true;
  }

  @Override
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
//  @Override
//  public Boolean logout(LogoutRequest request) {
//    var auth = SecurityContextHolder.getContext().getAuthentication();
//    if (auth == null) {
//      return false;
//    }
//
//    User user = null;
//    if (auth instanceof UsernamePasswordAuthenticationToken) {
//      user = (User) auth.getPrincipal();
//    }
//
//    if (user != null) {
//      new SecurityContextLogoutHandler().logout(request, response, auth);
//      SecurityContextHolder.getContext().setAuthentication(null);
//      auth.setAuthenticated(false);
//      return true;
//    }
//    return false;
//  }
}
