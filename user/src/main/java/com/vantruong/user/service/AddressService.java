package com.vantruong.user.service;

import com.vantruong.user.constant.MessageConstant;
import com.vantruong.user.entity.AddressData;
import com.vantruong.user.repository.AddressRepository;
import com.vantruong.common.exception.Constant;
import com.vantruong.common.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
  private final AddressRepository addressDataRepository;

  public AddressService(AddressRepository addressDataRepository) {
    this.addressDataRepository = addressDataRepository;
  }

  public List<AddressData> getAddressDataByParentCode(String parentCode) {
    return addressDataRepository.findAllByParentCodeOrderByName("null".equals(parentCode) ? null : parentCode);
  }

  public AddressData findAddressById(Integer id) {
    return addressDataRepository.findById(id).orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));
  }

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
