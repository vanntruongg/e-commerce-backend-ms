package addressdataservice.service;

import addressdataservice.dto.InternalUserAddressResponse;

public interface OrderDeliveryAddressService {
  InternalUserAddressResponse getDeliveryAddressById(Integer addressId);
}
