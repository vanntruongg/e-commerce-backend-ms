package identityservice.security;

import identityservice.entity.Role;
import identityservice.entity.User;
import identityservice.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsImpl extends User implements UserDetails {
  private transient User user;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
    for (Role role : user.getRoles()) {
      simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
    }
    return simpleGrantedAuthorityList;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  public Set<Role> getRole() {
    return user.getRoles();
  }

  public Boolean isActive() {
    return user.getStatus().equals(AccountStatus.ACTIVE);
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return !user.getStatus().equals(AccountStatus.PENDING_VERIFICATION);
  }
}
