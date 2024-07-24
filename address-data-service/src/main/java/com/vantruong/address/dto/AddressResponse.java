package com.vantruong.address.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
  private int id;
  private String name;
  private String phone;
  private String street;
  private AddressDetails province;
  private AddressDetails district;
  private AddressDetails ward;
  private boolean isDefault;
}
