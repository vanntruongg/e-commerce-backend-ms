package addressdataservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "address_data")
public class AddressData {
  @Id
  Integer id;
  @Column(name = "name", length = 100, nullable = false)
  String name;
  @Column(name = "slug", length = 100, nullable = false)
  String slug;
  @Column(name = "type", length = 20, nullable = false)
  String type;
  @Column(name = "code", length = 20, unique = true, nullable = false)
  String code;
  @Column(name = "parent_code", length = 20)
  String parentCode;
  @Column(name = "is_active")
  boolean isActive;
}
