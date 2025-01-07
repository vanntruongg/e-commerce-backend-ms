package com.vantruong.identity.security;

import com.vantruong.identity.entity.User;
import com.vantruong.identity.exception.ErrorCode;
import com.vantruong.identity.exception.NotFoundException;
import com.vantruong.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public UserDetailsImpl loadUserByUsername(String username) {
    User user = userRepository.findById(username).orElseThrow(()
            -> new NotFoundException(ErrorCode.NOT_FOUND, "User not found with email: " + username));
    return new UserDetailsImpl(user);
  }
}
