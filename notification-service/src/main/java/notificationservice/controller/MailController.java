package notificationservice.controller;

import lombok.RequiredArgsConstructor;
import notificationservice.dto.OrderDto;
import notificationservice.dto.SendMailVerifyUserRequest;
import notificationservice.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static notificationservice.constant.ApiEndpoint.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(MAIL_REQUEST_MAPPING)
public class MailController {
  private final MailService mailService;

  @PostMapping(VERIFY)
  public void sendVerificationEmail(@RequestBody SendMailVerifyUserRequest request) {
    mailService.sendVerificationEmail(request);
  }

  @PostMapping(FORGOT_PASSWORD)
  public void sendResetPassword(@RequestBody SendMailVerifyUserRequest request) {
    mailService.sendResetPassword(request);
  }

  @PostMapping(CONFIRM_ORDER)
  public void confirmOrder(@RequestBody OrderDto orderDto) {
    mailService.confirmOrder(orderDto);
  }
}
