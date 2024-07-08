package identityservice.service.Impl;

import identityservice.common.Utils;
import identityservice.constant.MessageConstant;
import identityservice.dto.request.IntrospectRequest;
import identityservice.dto.request.LoginRequest;
import identityservice.dto.request.RefreshTokenRequest;
import identityservice.dto.request.SendMailVerifyUserRequest;
import identityservice.dto.response.IntrospectResponse;
import identityservice.dto.response.LoginResponse;
import identityservice.entity.Token;
import identityservice.entity.User;
import identityservice.enums.AccountStatus;
import identityservice.enums.TokenType;
import identityservice.exception.*;
import identityservice.repository.UserRepository;
import identityservice.repository.client.MailClient;
import identityservice.security.JwtService;
import identityservice.security.UserDetailsImpl;
import identityservice.security.UserDetailsServiceImpl;
import identityservice.service.AuthService;
import identityservice.service.TokenService;
import identityservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
  JwtService jwtService;
  AuthenticationManager authenticationManager;
  UserDetailsServiceImpl userDetailsService;
  UserService userService;
  TokenService tokenService;
  UserRepository userRepository;
  MailClient mailClient;

  @Override
  public LoginResponse login(LoginRequest request) {
    var authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    // check if user has verified the account
    if (!userPrincipal.isEnabled()) {
      throw new UnVerifiedAccountException(ErrorCode.DENIED, MessageConstant.UNVERIFIED_ACCOUNT, new Throwable("unVerify"));
    }

    if (!userPrincipal.isActive()) {
      throw new AccountUnAvailableException(ErrorCode.DENIED, MessageConstant.ACCOUNT_UNAVAILABLE, new Throwable("unAvailable"));
    }

    String accessToken = jwtService.generateAccessToken(userPrincipal);
    String refreshToken = jwtService.generateRefreshToken(userPrincipal);
    return LoginResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
  }

  @Override
  public IntrospectResponse introspect(IntrospectRequest request) {
    var token = request.getToken();
    return IntrospectResponse.builder()
            .valid(jwtService.validateToken(token))
            .build();
  }

  @Override
  public LoginResponse refreshToken(RefreshTokenRequest request) {
    String token = request.getRefreshToken();

    if (!jwtService.validateToken(token)) {
      throw new BadCredentialsException("Invalid refresh token!");
    }
    String email = jwtService.getEmailFromToken(token);
    UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(email);

    String accessToken = jwtService.generateAccessToken(userDetails);

    return LoginResponse.builder()
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
  public Boolean logout(HttpServletRequest request, HttpServletResponse response) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      return false;
    }

    UserDetailsImpl userDetails = null;
    if (auth instanceof UsernamePasswordAuthenticationToken) {
      userDetails = (UserDetailsImpl) auth.getPrincipal();
    }

    if (userDetails != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
      SecurityContextHolder.getContext().setAuthentication(null);
      auth.setAuthenticated(false);
      return true;
    }
    return false;
  }

}
