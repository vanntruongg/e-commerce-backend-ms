package com.vantruong.identity.security;

import com.vantruong.identity.entity.Role;
import com.vantruong.identity.enums.AccountStatus;
import com.vantruong.identity.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
  private transient User user;
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();
    if (!CollectionUtils.isEmpty(user.getRoles()))
      user.getRoles().forEach(role -> {
      simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
        if (!CollectionUtils.isEmpty(role.getPermissions()))
          role.getPermissions().forEach(permission -> simpleGrantedAuthorityList.add(new SimpleGrantedAuthority(permission.getName())));
    });
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
