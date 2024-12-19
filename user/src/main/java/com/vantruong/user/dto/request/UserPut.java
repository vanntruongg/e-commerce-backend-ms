package com.vantruong.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserPut {
  private String email;
  private String firstName;
  private String lastName;
  private String phone;
  private String address;
  private String imageUrl;
  private List<String> roles;
}

