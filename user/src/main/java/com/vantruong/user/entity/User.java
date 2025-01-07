package com.vantruong.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user", schema = "public")
public class User {
  @Id
  String email;

  @Column(name = "first_name", length = 20)
  String firstName;

  @Column(name = "last_name", length = 20)
  String lastName;

  @Column(name = "dob")
  LocalDateTime dateOfBirth;

  @Column(name = "phone", length = 10)
  String phone;

  @Column(name = "image_url")
  String imageUrl;

}
