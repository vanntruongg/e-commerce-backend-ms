package com.vantruong.user.util;

import com.vantruong.user.dto.AddressDetail;
import com.vantruong.user.dto.UserAddressResponse;
import com.vantruong.user.entity.AddressData;
import com.vantruong.user.entity.UserAddress;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {

  public UserAddressResponse convertToUserAddressResponse(UserAddress userAddress) {
    return new UserAddressResponse(userAddress.getId(),
            userAddress.getName(),
            userAddress.getPhone(),
            userAddress.getStreet(),
            convertToAddressDetail(userAddress.getProvince()),
            convertToAddressDetail(userAddress.getDistrict()),
            convertToAddressDetail(userAddress.getWard()),
            userAddress.getIsDefault()
    );
  }

  public AddressDetail convertToAddressDetail(AddressData addressData) {
    return new AddressDetail(addressData.getId(),
            addressData.getName(),
            addressData.getType(),
            addressData.getCode(),
            addressData.getParentCode()
    );
  }
}
