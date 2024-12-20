package com.vantruong.identity.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserIdentity {
  private String email;
  private String password;
  private Set<String> roles;
}
