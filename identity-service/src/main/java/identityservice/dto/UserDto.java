package identityservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@Setter
public class UserDto {
  private String email;
  private String firstName;
  private String lastName;
  private LocalDate dob;
  private String phone;
  private String address;
  private String imageUrl;
  private List<String> roles;
}
