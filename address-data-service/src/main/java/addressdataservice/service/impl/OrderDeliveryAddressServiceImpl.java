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
import addressdataservice.service.OrderDeliveryAddressService;
import addressdataservice.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
