package com.vantruong.identity.dto;

import com.vantruong.identity.entity.Role;
import com.vantruong.identity.entity.User;

import java.time.LocalDate;
import java.util.Set;

public record UserDto(String email,
                      String firstName,
                      String lastName,
                      LocalDate dob,
                      String phone,
                      String imageUrl,
                      Set<Role> roles
) {
  public static UserDto fromEntity(User user) {
    return new UserDto(user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getDateOfBirth(),
            user.getPhone(),
            user.getImageUrl(),
            user.getRoles());
  }
}