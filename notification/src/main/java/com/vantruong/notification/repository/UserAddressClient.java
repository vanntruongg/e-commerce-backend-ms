package com.vantruong.notification.repository;

import com.vantruong.notification.common.CommonResponse;
import com.vantruong.notification.viewmodel.UserAddressVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.vantruong.notification.constant.InternalApiEndpoint.*;


@FeignClient(name = "user-service", url = USER_SERVICE_URL + INTERNAL)
public interface UserAddressClient {
  @GetMapping(ADDRESS + ID_PARAM)
  CommonResponse<UserAddressVm> getUserAddressById(@PathVariable("id") Integer addressId);
}
