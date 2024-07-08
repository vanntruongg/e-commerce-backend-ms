package addressdataservice.service.impl;

import addressdataservice.constant.MessageConstant;
import addressdataservice.dto.AddressDetails;
import addressdataservice.dto.AddressResponse;
import addressdataservice.entity.AddressData;
import addressdataservice.entity.UserAddress;
import addressdataservice.exception.ErrorCode;
import addressdataservice.exception.NotFoundException;
import addressdataservice.repository.AddressRepository;
import addressdataservice.service.AddressService;
import addressdataservice.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
  private final AddressRepository addressDataRepository;

  public List<AddressData> getAddressDataByParentCode(String parentCode) {
    return addressDataRepository.findAllByParentCode("null".equals(parentCode) ? null : parentCode);
  }

  @Override
  public AddressData findAddressById(Integer id) {
    return addressDataRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));
  }

  @Override
  public Boolean addressIsValid(Integer provinceId, Integer districtId, Integer wardId) {
    AddressData province = findAddressById(provinceId);
    List<AddressData> listDistrict = getAddressDataByParentCode(province.getCode());

    boolean hasDistrict = listDistrict.stream().anyMatch(district -> district.getId().equals(districtId));

   if(hasDistrict) {
     AddressData district = findAddressById(districtId);
     List<AddressData> listWard = getAddressDataByParentCode(district.getCode());
     return listWard.stream().anyMatch(ward -> ward.getId().equals(wardId));
   }
    return false;
  }

}
