package addressdataservice.service.impl;

import addressdataservice.constant.MessageConstant;
import addressdataservice.dto.AddressResponse;
import addressdataservice.dto.InternalUserAddressResponse;
import addressdataservice.dto.UserAddressRequest;
import addressdataservice.entity.AddressData;
import addressdataservice.entity.UserAddress;
import addressdataservice.exception.ErrorCode;
import addressdataservice.exception.NotFoundException;
import addressdataservice.repository.UserAddressRepository;
import addressdataservice.service.AddressService;
import addressdataservice.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {
  private final ModelMapper modelMapper;
  private final UserAddressRepository userAddressRepository;
  private final AddressService addressService;

  @Override
  public List<AddressResponse> getAllAddressByUserId(String email) {
    List<UserAddress> userAddresses = userAddressRepository.findAllByUserEmailOrderByIsDefaultDesc(email);
    return userAddresses.stream().map(address -> modelMapper.map(address, AddressResponse.class)).collect(Collectors.toList());
  }

  @Override
  public AddressResponse getAddressDefault(String email) {
    UserAddress address = getDefaultAddressByEmail(email);
    return modelMapper.map(address, AddressResponse.class);
  }

  private UserAddress getDefaultAddressByEmail(String email) {
    return userAddressRepository.findByUserEmailAndIsDefault(email, true)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));
  }

  @Override
  @Transactional
  public AddressResponse createUserAddress(UserAddressRequest request) {
//    check and set default address if it's first address
    List<UserAddress> userAddresses = userAddressRepository.findAllByUserEmail(request.getUserEmail());
    Boolean isDefault = userAddresses.isEmpty() || request.getIsDefault();

    // if there are existing address and the request is to set as default
    if (!userAddresses.isEmpty() && request.getIsDefault()) {
//       find and set the current default address to false
      UserAddress userAddressDefault = getDefaultAddressByEmail(request.getUserEmail());
      userAddressDefault.setIsDefault(false);
      userAddressRepository.save(userAddressDefault);
    }

    AddressData ward = addressService.findAddressById(request.getWardId());
    AddressData district = addressService.findAddressById(request.getDistrictId());
    AddressData province = addressService.findAddressById(request.getProvinceId());
    UserAddress userAddress = UserAddress.builder()
            .name(request.getName())
            .phone(request.getPhone())
            .street(request.getStreet())
            .ward(ward)
            .district(district)
            .province(province)
            .isDefault(isDefault)
            .userEmail(request.getUserEmail())
            .build();
    userAddressRepository.save(userAddress);
    return modelMapper.map(userAddress, AddressResponse.class);
  }

  @Override
  public UserAddress findById(Integer id) {
    return userAddressRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));
  }

  @Override
  @Transactional
  public Boolean updateUserAddress(UserAddressRequest request) {
    if (!addressService.addressIsValid(request.getProvinceId(), request.getDistrictId(), request.getWardId()))
      return false;

    AddressData ward = addressService.findAddressById(request.getWardId());
    AddressData district = addressService.findAddressById(request.getDistrictId());
    AddressData province = addressService.findAddressById(request.getProvinceId());

    UserAddress userAddress = findById(request.getId());

    userAddress.setName(request.getName());
    userAddress.setPhone(request.getPhone());
    userAddress.setStreet(request.getStreet());
    userAddress.setWard(ward);
    userAddress.setDistrict(district);
    userAddress.setProvince(province);
    userAddressRepository.save(userAddress);

    return true;
  }

  @Override
  @Transactional
  public Boolean setDefaultAddress(String email, Integer addressId) {
    UserAddress userAddress = findById(addressId);

    UserAddress currentDefaultAddress = getDefaultAddressByEmail(email);

    if (userAddress.equals(currentDefaultAddress)) return true;

    currentDefaultAddress.setIsDefault(false);
    userAddressRepository.save(currentDefaultAddress);

    userAddress.setIsDefault(true);
    userAddressRepository.save(userAddress);
    return true;
  }

  @Override
  public Boolean deleteAddress(String email, Integer addressId) {
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
