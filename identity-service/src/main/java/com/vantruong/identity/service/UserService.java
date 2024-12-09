package com.vantruong.identity.service;

import com.vantruong.common.dto.user.UserCommonDto;
import com.vantruong.common.exception.Constant;
import com.vantruong.common.exception.DuplicateException;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.identity.common.Utils;
import com.vantruong.identity.constant.MessageConstant;
import com.vantruong.identity.dto.UserDto;
import com.vantruong.identity.dto.UserListDto;
import com.vantruong.identity.dto.request.*;
import com.vantruong.identity.entity.Role;
import com.vantruong.identity.entity.Token;
import com.vantruong.identity.entity.User;
import com.vantruong.identity.enums.AccountStatus;
import com.vantruong.identity.enums.ERole;
import com.vantruong.identity.enums.TokenType;
import com.vantruong.identity.exception.ExpiredException;
import com.vantruong.identity.exception.FormException;
import com.vantruong.identity.repository.UserRepository;
import com.vantruong.identity.repository.client.MailClient;
import com.vantruong.identity.security.SecurityContextHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class UserService {
  UserRepository userRepository;
  PasswordEncoder passwordEncoder;
  SecurityContextHelper securityContextHelper;
  MailClient mailClient;
  TokenService tokenService;
  RoleService roleService;

  public Boolean register(RegisterRequest userDto) {
    try {
      if (userRepository.existsById(userDto.getEmail())) {
        throw new FormException(Constant.ErrorCode.NOT_NULL, MessageConstant.EMAIL_EXISTED, new Throwable("email"));
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

  public Boolean requestForgotPassword(String email) {
    Token token = Utils.generateToken(TokenType.RESET_PASSWORD);
    User user = getUserByEmail(email);
    token.setUser(user);
    tokenService.createToken(token);
    SendMailVerifyUserRequest request = SendMailVerifyUserRequest.builder()
            .email(user.getEmail())
            .name(user.getFirstName())
            .token(token.getTokenValue())
            .build();
    mailClient.sendForgotPassword(request);
    return true;
  }

  public Boolean resetPassword(ResetPasswordRequest request) {
    Token token = tokenService.findByTokenValue(request.getToken());
    User user = token.getUser();
    if (TokenType.RESET_PASSWORD.getTokenType().equals(token.getTokenType())) {
      if (LocalDateTime.now().isBefore(token.getExpiredDate())) {
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
      } else {
        throw new ExpiredException(Constant.ErrorCode.EXPIRED, MessageConstant.EXPIRED_TOKEN);
      }
    } else {
      throw new NotFoundException(Constant.ErrorCode.DENIED, MessageConstant.INVALID_TOKEN);
    }
    userRepository.save(user);
    return true;
  }

  public UserDto existedByEmail(String email) {
    return UserDto.fromEntity(findByEmail(email));
  }

  @PreAuthorize("hasRole('ADMIN')")
  public UserListDto getAllUser(int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize);

    Page<User> userPage = userRepository.findAll(pageable);

    List<UserDto> userDtoList = userPage.getContent().stream()
            .map(UserDto::fromEntity)
            .toList();

    return new UserListDto(
            userDtoList,
            (int) userPage.getTotalElements(),
            userPage.getTotalPages(),
            userPage.isLast()
    );
  }

  public UserListDto searchByName(String name, int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize);

    Page<User> userPage = userRepository.findByFirstNameContainingIgnoreCase(name, pageable);
    List<UserDto> userDtoList = userPage.getContent().stream()
            .map(UserDto::fromEntity)
            .toList();

    return new UserListDto(
            userDtoList,
            (int) userPage.getTotalElements(),
            userPage.getTotalPages(),
            userPage.isLast()
    );
  }

  @PostAuthorize("returnObject.email == authentication.name")
  public User getUserByEmail(String email) {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    authentication.getAuthorities().forEach(authority -> log.info(authority.getAuthority()));
    return userRepository.findById(email).orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.USER_NOT_FOUND));
  }

  public User findByEmail(String email) {
    return userRepository.findById(email)
            .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.USER_NOT_FOUND));
  }

  @Transactional
  public UserDto updateUser(UserPut userPut) {
    Set<Role> accountRoles = securityContextHelper.getRoles();

    User user = getUserByEmail(userPut.getEmail());

    user.setFirstName(userPut.getFirstName());
    user.setLastName(userPut.getLastName());
    user.setPhone(userPut.getPhone());
    user.setImageUrl(userPut.getImageUrl());

    // if list role not empty and account update is admin
    Role adminRole = roleService.findById(ERole.ADMIN.getRole());
    if (userPut.getRoles() != null && accountRoles.contains(adminRole)) {
      List<Role> roles = roleService.findAllById(userPut.getRoles());
      user.setRoles(new HashSet<>(roles));
    }
    User savedUser = userRepository.save(user);
    return UserDto.fromEntity(savedUser);
  }

  @Transactional
  public Boolean changePassword(ChangePasswordRequest changePasswordRequest) {
    String email = securityContextHelper.getUserId();
    User user = getUserByEmail(email);

    if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
      user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
      userRepository.save(user);
      return true;
    } else {
      throw new FormException(Constant.ErrorCode.DENIED, MessageConstant.OLD_PASSWORD_NOT_MATCHES, new Throwable("oldPassword"));
    }
  }

  public Boolean deleteUser(String email) {
    User user = getUserByEmail(email);
    user.setStatus(AccountStatus.DELETED);
    userRepository.save(user);
    return true;
  }

  public UserCommonDto getProfile() {
    String email = securityContextHelper.getUserId();
    User user = getUserByEmail(email);
    return new UserCommonDto(user.getEmail(), user.getFirstName(), user.getLastName());
  }

  public Boolean addPhoneNumber(String phone) {
    String email = securityContextHelper.getUserId();
    User user = getUserByEmail(email);
    user.setPhone(phone);
    userRepository.save(user);
    return true;
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
  public Long getUserCount() {
    return userRepository.count();
  }

  @PostAuthorize("returnObject.email == authentication.name")
  public Object getUser() {
    String email = securityContextHelper.getUserId();
    return getUserByEmail(email);
  }

  public boolean activeAccount(String email) {
    User user = findByEmail(email);
    user.setStatus(AccountStatus.ACTIVE);
    userRepository.save(user);
    return true;
  }
}
