package com.vantruong.identity.entity;

import jakarta.persistence.*;
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
public class Token {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer id;
  String tokenType;
  String tokenValue;
  LocalDateTime expiredDate;
  @ManyToOne
  @JoinColumn(name = "user_email")
  User user;
}
