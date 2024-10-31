package com.vantruong.address.service;

import com.vantruong.address.constant.MessageConstant;
import com.vantruong.address.dto.UserAddressRequest;
import com.vantruong.address.dto.UserAddressResponse;
import com.vantruong.address.entity.AddressData;
import com.vantruong.address.entity.UserAddress;
import com.vantruong.address.exception.ErrorCode;
import com.vantruong.address.exception.NotFoundException;
import com.vantruong.address.repository.UserAddressRepository;
import com.vantruong.address.util.AddressConverter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAddressService {
  UserAddressRepository userAddressRepository;
  AddressService addressService;
  AddressConverter addressConverter;
  AuthService authService;

  public List<UserAddressResponse> getAllAddressByUserId() {
    String email = authService.getUserId();
    List<UserAddress> userAddresses = userAddressRepository.findAllByUserEmailOrderByIsDefaultDesc(email);
    return userAddresses.stream()
            .map(addressConverter::convertToUserAddressResponse)
            .toList();
  }

  public UserAddressResponse getAddressDefault() {
    Optional<UserAddress> address = getDefaultAddressByEmail();
    return address.map(addressConverter::convertToUserAddressResponse).orElse(null);
  }

  private Optional<UserAddress> getDefaultAddressByEmail() {
    String email = authService.getUserId();
    return userAddressRepository.findByUserEmailAndIsDefault(email, true);
  }

  @Transactional
  public UserAddressResponse createUserAddress(UserAddressRequest request) {
    String email = authService.getUserId();
//    check and set default address if it's first address
    List<UserAddress> userAddresses = userAddressRepository.findAllByUserEmail(email);
    Boolean isDefault = userAddresses.isEmpty() || request.isDefault();

    // if there are existing address and the request is to set as default
    if (!userAddresses.isEmpty() && request.isDefault()) {
//       find and set the current default address to false
      Optional<UserAddress> userAddressDefault = getDefaultAddressByEmail();
      if (userAddressDefault.isPresent()) {
        userAddressDefault.get().setIsDefault(false);
        userAddressRepository.save(userAddressDefault.get());
      }
    }

    AddressData ward = addressService.findAddressById(request.wardId());
    AddressData district = addressService.findAddressById(request.districtId());
    AddressData province = addressService.findAddressById(request.provinceId());
    UserAddress userAddress = UserAddress.builder()
            .name(request.name())
            .phone(request.phone())
            .street(request.street())
            .ward(ward)
            .district(district)
            .province(province)
            .isDefault(isDefault)
            .userEmail(email)
            .build();
    userAddressRepository.save(userAddress);
    return addressConverter.convertToUserAddressResponse(userAddress);
  }

  public UserAddress findById(Integer id) {
    return userAddressRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));
  }

  @Transactional
  public Boolean updateUserAddress(UserAddressRequest request) {
    if (!addressService.addressIsValid(request.provinceId(), request.districtId(), request.wardId()))
      return false;

    AddressData ward = addressService.findAddressById(request.wardId());
    AddressData district = addressService.findAddressById(request.districtId());
    AddressData province = addressService.findAddressById(request.provinceId());

    UserAddress userAddress = findById(request.id());

    userAddress.setName(request.name());
    userAddress.setPhone(request.phone());
    userAddress.setStreet(request.street());
    userAddress.setWard(ward);
    userAddress.setDistrict(district);
    userAddress.setProvince(province);
    userAddressRepository.save(userAddress);

    return true;
  }

  @Transactional
  public Boolean setDefaultAddress(Integer addressId) {
    UserAddress userAddress = findById(addressId);

    Optional<UserAddress> currentDefaultAddress = getDefaultAddressByEmail();
    if (currentDefaultAddress.isPresent()) {
      UserAddress address = currentDefaultAddress.get();
      if (userAddress.equals(address)) return true;
      address.setIsDefault(false);
      userAddressRepository.save(address);
    }

    userAddress.setIsDefault(true);
    userAddressRepository.save(userAddress);
    return true;

  }

  public Boolean deleteAddress(Integer addressId) {
    String email = authService.getUserId();
    List<UserAddress> addresses = userAddressRepository.findAllByUserEmail(email);
    UserAddress userAddress = addresses.stream()
            .filter(address -> address.getId().equals(addressId))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));

//    check if there are more than one address
    if (addresses.size() > 1 && userAddress.getIsDefault()) return false;

    userAddressRepository.delete(userAddress);

    return true;
  }

}
