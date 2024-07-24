package com.vantruong.address.service.impl;

import com.vantruong.address.dto.InternalUserAddressResponse;
import com.vantruong.address.entity.UserAddress;
import com.vantruong.address.service.OrderDeliveryAddressService;
import com.vantruong.address.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDeliveryAddressServiceImpl implements OrderDeliveryAddressService {
  private final UserAddressService userAddressService;


  @Override
  public InternalUserAddressResponse getDeliveryAddressById(Integer addressId) {
    UserAddress userAddress = userAddressService.findById(addressId);
    return InternalUserAddressResponse.builder()
            .id(userAddress.getId())
            .name(userAddress.getName())
            .phone(userAddress.getPhone())
            .street(userAddress.getStreet())
            .ward(userAddress.getWard().getName())
            .district(userAddress.getDistrict().getName())
            .province(userAddress.getProvince().getName())
            .build();
  }

}
