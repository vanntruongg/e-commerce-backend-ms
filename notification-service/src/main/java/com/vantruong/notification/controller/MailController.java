package com.vantruong.notification.controller;

import com.vantruong.notification.constant.ApiEndpoint;
import com.vantruong.notification.viewmodel.SendMailVerifyUserRequest;
import com.vantruong.notification.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(ApiEndpoint.MAIL_REQUEST_MAPPING)
public class MailController {
  private final MailService mailService;

  public MailController(MailService mailService) {
    this.mailService = mailService;
  }

  @PostMapping(ApiEndpoint.VERIFY)
  public void sendVerificationEmail(@RequestBody SendMailVerifyUserRequest request) {
    mailService.sendVerificationEmail(request);
  }

  @PostMapping(ApiEndpoint.FORGOT_PASSWORD)
  public void sendResetPassword(@RequestBody SendMailVerifyUserRequest request) {
    mailService.sendResetPassword(request);
  }

//  @PostMapping(ApiEndpoint.CONFIRM_ORDER)
//  public void confirmOrder(@RequestBody OrderVm orderDto) {
//    mailService.confirmOrder(orderDto);
//  }
}
