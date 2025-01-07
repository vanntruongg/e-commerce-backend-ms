package com.vantruong.user.dto;

import com.vantruong.user.entity.User;

import java.util.Set;

public record UserDto(String email,
                      String firstName,
                      String lastName,
                      String phone,
                      String status,
                      String imageUrl,
                      Set<Role> roles
) {
  public static UserDto fromEntity(User user) {
    return new UserDto(user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhone(),
            user.getStatus().name(),
            user.getImageUrl(),
            user.getRoles());
  }
}