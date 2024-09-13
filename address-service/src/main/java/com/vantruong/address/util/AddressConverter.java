package com.vantruong.address.util;

import com.vantruong.address.dto.AddressDetail;
import com.vantruong.address.dto.UserAddressResponse;
import com.vantruong.address.entity.AddressData;
import com.vantruong.address.entity.UserAddress;
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
