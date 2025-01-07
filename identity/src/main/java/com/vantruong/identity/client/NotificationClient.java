package com.vantruong.identity.client;

import com.vantruong.identity.common.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static com.vantruong.identity.constant.InternalApiEndpoint.*;


@FeignClient(name = "notification-service", url = NOTIFICATION_SERVICE_URL + INTERNAL)
public interface NotificationClient {
  @PostMapping("")
  CommonResponse<?> findById();
}
