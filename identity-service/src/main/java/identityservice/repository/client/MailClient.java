package identityservice.repository.client;

import identityservice.dto.request.SendMailVerifyUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static identityservice.constant.InternalApiEndpoint.*;

@FeignClient(name = "mail-service", url = MAIL_SERVICE_URL)
public interface MailClient {

  @PostMapping(VERIFY)
  void sendVerificationEmail(@RequestBody SendMailVerifyUserRequest request);

  @PostMapping(FORGOT_PASSWORD)
  void sendForgotPassword(@RequestBody SendMailVerifyUserRequest request);
}