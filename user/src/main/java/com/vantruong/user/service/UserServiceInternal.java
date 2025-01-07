package com.vantruong.user.service;

import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.entity.User;
import com.vantruong.user.enums.AccountStatus;
import com.vantruong.user.exception.ErrorCode;
import com.vantruong.user.exception.NotFoundException;
import com.vantruong.user.repository.UserRepository;
import com.vantruong.user.viewmodel.UserIdentityVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceInternal {
  private final UserRepository userRepository;

  public UserIdentityVm findById(String email) {
    User user = userRepository.findById(email)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.ACCOUNT_NOT_FOUND));

    Set<String> roles = user.getRoles().stream()
            .map(Role::getName)
            .collect(Collectors.toSet());

    return new UserIdentityVm(
            user.getEmail(),
            user.getPassword(),
            user.getStatus().equals(AccountStatus.ACTIVE),
            roles
    );
  }
}
