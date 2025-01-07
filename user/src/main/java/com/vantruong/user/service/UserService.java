package com.vantruong.user.service;

import com.vantruong.user.client.MailClient;
import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.dto.UserDto;
import com.vantruong.user.dto.UserListDto;
import com.vantruong.user.dto.request.*;
import com.vantruong.user.entity.User;
import com.vantruong.user.exception.*;
import com.vantruong.user.helper.SecurityContextHelper;
import com.vantruong.user.repository.UserRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
  UserRepository userRepository;
  SecurityContextHelper securityContextHelper;
  MailClient mailClient;
  TokenService tokenService;
  RoleService roleService;

  public Boolean createNewUser(CreateUserRequest userDto) {
    try {
      if (userRepository.existsById(userDto.getEmail())) {
        throw new FormException(ErrorCode.NOT_NULL, MessageConstant.EMAIL_EXISTED, new Throwable("email"));
      }

//      Token tokenVerifyUser = Utils.generateToken(TokenType.VERIFICATION);
//      Role role = roleService.findById(ERole.USER.getRole());
      User newUser = User.builder()
              .email(userDto.getEmail())
              .firstName(userDto.getFirstName())
              .lastName(userDto.getLastName())
              .build();

      userRepository.save(newUser);
//
//      SendMailVerifyUserRequest request = SendMailVerifyUserRequest.builder()
//              .email(newUser.getEmail())
//              .name(newUser.getFirstName())
//              .token(tokenVerifyUser.getTokenValue())
//              .build();
//      mailClient.sendVerificationEmail(request);
      return true;
    } catch (DuplicateException ex) {
      log.error("Error during user registration: {}", ex.getMessage(), ex);
      throw ex;
    }
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
    return userRepository.findById(email).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.USER_NOT_FOUND));
  }

  public User findByEmail(String email) {
    return userRepository.findById(email)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.USER_NOT_FOUND));
  }

  @Transactional
  public UserDto updateUser(UserPut userPut) {
    Set<String> accountRoles = securityContextHelper.getRoles();

    User user = getUserByEmail(userPut.getEmail());

    user.setFirstName(userPut.getFirstName());
    user.setLastName(userPut.getLastName());
    user.setPhone(userPut.getPhone());
    user.setImageUrl(userPut.getImageUrl());

    // if list role not empty and account update is admin
//    Role adminRole = roleService.findById(ERole.ADMIN.getRole());
//    if (userPut.getRoles() != null && accountRoles.contains(adminRole.getName())) {
//      List<Role> roles = roleService.findAllById(userPut.getRoles());
//      user.setRoles(new HashSet<>(roles));
//    }
    User savedUser = userRepository.save(user);
    return UserDto.fromEntity(savedUser);
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

}
