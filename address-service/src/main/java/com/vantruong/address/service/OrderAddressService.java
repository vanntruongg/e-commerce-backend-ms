package com.vantruong.address.service;

import com.vantruong.address.dto.UserAddressResponse;
import com.vantruong.address.entity.UserAddress;
import com.vantruong.address.util.AddressConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderAddressService {
  private final UserAddressService userAddressService;
  AddressConverter addressConverter;

  public UserAddressResponse getOrderAddressById(Integer addressId) {
    UserAddress userAddress = userAddressService.findById(addressId);
    return addressConverter.convertToUserAddressResponse(userAddress);
  }

}
