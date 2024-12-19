package com.vantruong.user.service;

import com.vantruong.user.dto.UserAddressResponse;
import com.vantruong.user.entity.UserAddress;
import com.vantruong.user.util.AddressConverter;
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
