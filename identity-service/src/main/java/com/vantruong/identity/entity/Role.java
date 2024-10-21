package com.vantruong.identity.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
  @Id
  @Column(name = "role_name")
  String name;
  @Max(value = 20)
  @Column(name = "role_description")
  String description;

  @ManyToMany
  @JoinTable(name = "role_permissions",
          joinColumns = @JoinColumn(name = "role_name"),
          inverseJoinColumns = @JoinColumn(name = "permission_name")
  )
  Set<Permission> permissions;
}
