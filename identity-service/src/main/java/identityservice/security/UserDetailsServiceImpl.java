package identityservice.security;

import identityservice.entity.User;
import identityservice.exception.ErrorCode;
import identityservice.exception.FormException;
import identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = repository.findById(username).orElseThrow(() ->
            new FormException(ErrorCode.FORM_ERROR, "Tài khoản không tồn tại.", new Throwable("email"))
    );

    return new UserDetailsImpl(user);
  }
}
