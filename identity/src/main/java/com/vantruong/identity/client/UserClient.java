package com.vantruong.identity.client;

import com.vantruong.identity.common.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static com.vantruong.identity.constant.InternalApiEndpoint.INTERNAL;
import static com.vantruong.identity.constant.InternalApiEndpoint.USER_SERVICE_URL;


@FeignClient(name = "user-service", url = USER_SERVICE_URL + INTERNAL)
public interface UserClient {
//  @GetMapping("/get/{email}")
//  CommonResponse<?> findById(@PathVariable("email") String email);
}
