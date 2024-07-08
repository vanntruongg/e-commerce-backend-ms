package addressdataservice.service;

import addressdataservice.dto.AddressResponse;
import addressdataservice.dto.UserAddressRequest;

import java.util.List;

public interface UserAddressService {

  List<AddressResponse> getAllAddressByUserId(String email);

  AddressResponse getAddressDefault(String email);

  Boolean createUserAddress(UserAddressRequest request);

  Boolean updateUserAddress(UserAddressRequest request);

  Boolean setDefaultAddress(String email, Integer id);

  Boolean deleteAddress(String email, Integer addressId);
}
