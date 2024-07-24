package com.vantruong.address.service;

import com.vantruong.address.dto.AddressResponse;
import com.vantruong.address.dto.UserAddressRequest;
import com.vantruong.address.entity.UserAddress;

import java.util.List;

public interface UserAddressService {

  List<AddressResponse> getAllAddressByUserId(String email);
  UserAddress findById(Integer addressId);

  AddressResponse getAddressDefault(String email);

  AddressResponse createUserAddress(UserAddressRequest request);

  Boolean updateUserAddress(UserAddressRequest request);

  Boolean setDefaultAddress(String email, Integer id);

  Boolean deleteAddress(String email, Integer addressId);

}
