package identityservice.security;

import identityservice.constant.MessageConstant;
import identityservice.entity.Role;
import identityservice.entity.User;
import identityservice.exception.ErrorCode;
import identityservice.exception.FormException;
import identityservice.repository.UserRepository;
import identityservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  private Collection<? extends GrantedAuthority> getAuthorities(User user) {
    List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
    for (Role role : user.getRoles()) {
      simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
    }
    return simpleGrantedAuthorityList;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    User user = userService.findUserByEmail(username);

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new FormException(ErrorCode.FORM_ERROR, MessageConstant.PASSWORD_INCORRECT,  new Throwable("password"));
    }
    return new UsernamePasswordAuthenticationToken(user, password, getAuthorities(user));
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
