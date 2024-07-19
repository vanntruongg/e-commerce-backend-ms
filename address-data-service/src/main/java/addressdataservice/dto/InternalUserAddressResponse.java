package addressdataservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternalUserAddressResponse {
  private int id;
  private String name;
  private String phone;
  private String street;
  private String province;
  private String district;
  private String ward;
}
