package com.vantruong.user.client;

import com.vantruong.user.dto.request.SendMailVerifyUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.vantruong.user.constant.InternalApiEndpoint.MAIL_SERVICE_URL;
import static com.vantruong.user.constant.InternalApiEndpoint.VERIFY;
import static com.vantruong.user.constant.UserApiEndpoint.FORGOT_PASSWORD;


@FeignClient(name = "mail-service", url = MAIL_SERVICE_URL)
public interface MailClient {

  @PostMapping(VERIFY)
  void sendVerificationEmail(@RequestBody SendMailVerifyUserRequest request);

  @PostMapping(FORGOT_PASSWORD)
  void sendForgotPassword(@RequestBody SendMailVerifyUserRequest request);
}