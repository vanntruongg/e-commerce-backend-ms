package com.vantruong.notification.repository;

import com.vantruong.common.constant.InternalApiEndpoint;
import com.vantruong.common.dto.UserAddress;
import com.vantruong.notification.common.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "address-data-service", url = InternalApiEndpoint.ADDRESS_SERVICE_URL)
public interface UserAddressClient {
  @GetMapping(InternalApiEndpoint.ORDER + InternalApiEndpoint.GET + InternalApiEndpoint.ID_PARAM)
  CommonResponse<UserAddress> getUserAddressById(@PathVariable("id") Integer addressId);
}
