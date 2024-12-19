package com.vantruong.identity.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AddressRequest {
  private Integer wardId;
  private Integer districtId;
  private Integer provinceId;
}
