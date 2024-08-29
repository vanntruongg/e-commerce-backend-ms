package com.vantruong.address.service;

import com.vantruong.address.dto.InternalUserAddressResponse;

public interface OrderDeliveryAddressService {
  InternalUserAddressResponse getDeliveryAddressById(Integer addressId);
}
