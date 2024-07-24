package com.vantruong.address.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAddressRequest {
  private Integer id;
  private String name;
  private String phone;
  private String street;
  private Integer wardId;
  private Integer districtId;
  private Integer provinceId;
  private Boolean isDefault;
  private String userEmail;
}
