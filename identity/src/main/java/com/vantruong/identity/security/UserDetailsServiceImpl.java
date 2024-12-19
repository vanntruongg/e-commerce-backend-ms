package com.vantruong.identity.security;

import com.vantruong.common.exception.Constant;
import com.vantruong.identity.entity.User;
import com.vantruong.identity.exception.FormException;
import com.vantruong.identity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository repository;

  public UserDetailsServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetailsImpl loadUserByUsername(String username) {
    User user = repository.findById(username)
            .orElseThrow(() ->
                    new FormException(Constant.ErrorCode.FORM_ERROR, "Tài khoản không tồn tại.", new Throwable("email"))
            );
    return new UserDetailsImpl(user);
  }
}
