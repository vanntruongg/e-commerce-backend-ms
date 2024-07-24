package com.vantruong.address.service;

import com.vantruong.address.entity.AddressData;

import java.util.List;

public interface AddressService {
  List<AddressData> getAddressDataByParentCode(String parentCode);

  AddressData findAddressById(Integer wardId);

  Boolean addressIsValid(Integer provinceId, Integer districtId, Integer wardId);
}
