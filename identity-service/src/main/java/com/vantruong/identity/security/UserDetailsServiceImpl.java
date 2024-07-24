package com.vantruong.identity.security;

import com.vantruong.identity.exception.ErrorCode;
import com.vantruong.identity.entity.User;
import com.vantruong.identity.exception.FormException;
import com.vantruong.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository repository;

  @Override
  public UserDetailsImpl loadUserByUsername(String username) {
    User user = repository.findById(username)
            .orElseThrow(() ->
                    new FormException(ErrorCode.FORM_ERROR, "Tài khoản không tồn tại.", new Throwable("email"))
            );
    return new UserDetailsImpl(user);
  }
}
