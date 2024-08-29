package com.vantruong.common.util;

import com.vantruong.common.dto.UserAddress;
import org.springframework.stereotype.Component;

@Component
public class AddressFormatter {

  public String formatAddress(UserAddress userAddress) {
    return userAddress.getStreet() + ", " + userAddress.getWard() + ", " + userAddress.getDistrict() + ", " + userAddress.getProvince();
  }
}
