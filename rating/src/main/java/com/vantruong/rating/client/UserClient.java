package com.vantruong.rating.client;

import com.vantruong.rating.common.CommonResponse;
import com.vantruong.rating.viewmodel.UserVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import static com.vantruong.rating.constant.InternalApiEndpoint.INTERNAL;
import static com.vantruong.rating.constant.InternalApiEndpoint.USER_SERVICE_URL;

@FeignClient(name = "identity-service", url = USER_SERVICE_URL + INTERNAL)
public interface UserClient {

  @GetMapping("/user/profile")
  CommonResponse<UserVm> getUserProfile();
}
