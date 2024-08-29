package com.vantruong.identity.service.Impl;

import com.vantruong.common.exception.DuplicateException;
import com.vantruong.common.exception.ErrorCode;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.identity.common.Utils;
import com.vantruong.identity.constant.MessageConstant;
import com.vantruong.identity.dto.UserDto;
import com.vantruong.identity.dto.request.ChangePasswordRequest;
import com.vantruong.identity.dto.request.RegisterRequest;
import com.vantruong.identity.dto.request.ResetPasswordRequest;
import com.vantruong.identity.dto.request.SendMailVerifyUserRequest;
import com.vantruong.identity.entity.Role;
import com.vantruong.identity.entity.Token;
import com.vantruong.identity.entity.User;
import com.vantruong.identity.enums.AccountStatus;
import com.vantruong.identity.enums.ERole;
import com.vantruong.identity.enums.TokenType;
import com.vantruong.identity.exception.*;
import com.vantruong.identity.repository.UserRepository;
import com.vantruong.identity.repository.client.MailClient;
import com.vantruong.identity.security.SecurityContextHelper;
import com.vantruong.identity.service.RoleService;
import com.vantruong.identity.service.TokenService;
import com.vantruong.identity.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
  UserRepository userRepository;
  PasswordEncoder passwordEncoder;
  SecurityContextHelper securityContextHelper;
  MailClient mailClient;
  TokenService tokenService;
  RoleService roleService;

  @Override
  @Transactional
  public Boolean register(RegisterRequest userDto) {
    try {
      if (userRepository.existsById(userDto.getEmail())) {
        throw new FormException(ErrorCode.NOT_NULL, MessageConstant.EMAIL_EXISTED, new Throwable("email"));
      }

      Token tokenVerifyUser = Utils.generateToken(TokenType.VERIFICATION);
      Role role = roleService.findById(ERole.USER.getRole());
      User newUser = User.builder()
              .email(userDto.getEmail())
              .firstName(userDto.getFirstName())
              .lastName(userDto.getLastName())
              .password(passwordEncoder.encode(userDto.getPassword()))
              .roles(Set.of(role))
              .build();

      userRepository.save(newUser);

      SendMailVerifyUserRequest request = SendMailVerifyUserRequest.builder()
              .email(newUser.getEmail())
              .name(newUser.getFirstName())
              .token(tokenVerifyUser.getTokenValue())
              .build();
      mailClient.sendVerificationEmail(request);
      return true;
    } catch (DuplicateException ex) {
      log.error("Error during user registration: {}", ex.getMessage(), ex);
      throw ex;
    }
  }

  @Override
  public Boolean requestForgotPassword(String email) {
    Token token = Utils.generateToken(TokenType.RESET_PASSWORD);
    User user = getUserByEmail(email);

    SendMailVerifyUserRequest request = SendMailVerifyUserRequest.builder()
            .email(user.getEmail())
            .name(user.getFirstName())
            .token(token.getTokenValue())
            .build();
    mailClient.sendForgotPassword(request);
    userRepository.save(user);
    return true;
  }

  @Override
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

  @Override
  public User existedByEmail(String email) {
    return findUserByEmail(email);
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public List<User> getAllUser() {
    return userRepository.findAll();
  }

  @Override
  @PostAuthorize("returnObject.email == authentication.name")
  public User getUserByEmail(String email) {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    authentication.getAuthorities().forEach(authority -> log.info(authority.getAuthority()));
    return userRepository.findById(email).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.USER_NOT_FOUND));
  }

  public User findUserByEmail(String email) {
    return findUserByEmailInternal(email);
  }

  private User findUserByEmailInternal(String email) {
    return userRepository.findById(email)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.USER_NOT_FOUND));
  }

  @Override
  @Transactional
  public User updateUser(UserDto userDto) {
    Set<Role> accountRoles = securityContextHelper.getRoles();

    User user = getUserByEmail(userDto.getEmail());

    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setDateOfBirth(userDto.getDob());
    user.setPhone(userDto.getPhone());
    user.setImageUrl(userDto.getImageUrl());

    // if list role not empty and account update is admin
    Role adminRole = roleService.findById(ERole.ADMIN.getRole());
    if (userDto.getRoles() != null && accountRoles.contains(adminRole)) {
      List<Role> roles = roleService.findAllById(userDto.getRoles());
      user.setRoles(new HashSet<>(roles));
    }
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public Boolean changePassword(ChangePasswordRequest changePasswordRequest) {
    String email = securityContextHelper.getUserId();
    User user = getUserByEmail(email);

    if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
      user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
      userRepository.save(user);
      return true;
    } else {
      throw new FormException(ErrorCode.DENIED, MessageConstant.OLD_PASSWORD_NOT_MATCHES, new Throwable("oldPassword"));
    }
  }

  @Override
  public Boolean deleteUser(String email) {
    User user = getUserByEmail(email);
    user.setStatus(AccountStatus.DELETED);
    userRepository.save(user);
    return true;
  }

  @Override
  @PostAuthorize("returnObject.email == authentication.name")
  public User getProfile() {
    String email = securityContextHelper.getUserId();
    return getUserByEmail(email);
  }

  @Override
  public Boolean addPhoneNumber(String phone) {
    String email = securityContextHelper.getUserId();
    User user = getUserByEmail(email);
    user.setPhone(phone);
    userRepository.save(user);
    return true;
  }

  @Override
  @PreAuthorize("hasRole('ADMIN')")
  public Long getUserCount() {
    return userRepository.count();
  }

}
