package identityservice.entity;

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
  String name;
  @Max(value = 20)
  String description;

  @ManyToMany
  @JoinTable(name = "role_permissions",
          joinColumns = @JoinColumn(name = "role_name"),
          inverseJoinColumns = @JoinColumn(name = "permission_name")
  )
  Set<Permission> permissions;
}
