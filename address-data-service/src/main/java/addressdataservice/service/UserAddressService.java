package addressdataservice.service;

import addressdataservice.dto.AddressResponse;
import addressdataservice.dto.InternalUserAddressResponse;
import addressdataservice.dto.UserAddressRequest;
import addressdataservice.entity.UserAddress;

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
