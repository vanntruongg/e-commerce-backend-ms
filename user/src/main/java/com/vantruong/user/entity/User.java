package com.vantruong.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vantruong.user.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

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

  @Column(name = "phone", length = 10)
  String phone;

  @Column(name = "image_url")
  String imageUrl;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_status")
  @Builder.Default
  AccountStatus status = AccountStatus.PENDING_VERIFICATION;
  @JsonIgnore
  String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles",
          joinColumns = @JoinColumn(name = "user_email"),
          inverseJoinColumns = @JoinColumn(name = "role_name")
  )
  Set<Role> roles;

}
