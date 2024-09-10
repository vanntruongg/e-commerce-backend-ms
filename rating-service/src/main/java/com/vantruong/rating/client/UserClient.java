package com.vantruong.rating.client;

import com.vantruong.common.dto.user.UserCommonDto;
import com.vantruong.rating.common.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import static com.vantruong.common.constant.InternalApiEndpoint.IDENTITY_SERVICE_URL;

@FeignClient(name = "identity-service", url = IDENTITY_SERVICE_URL)
public interface UserClient {

  @GetMapping("/users/profile")
  CommonResponse<UserCommonDto> getUserProfile();
}
