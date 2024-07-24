package com.vantruong.notification.service.impl;

import com.vantruong.notification.constant.EmailConstant;
import com.vantruong.notification.dto.SendMailVerifyUserRequest;
import com.vantruong.notification.service.MailService;
import lombok.RequiredArgsConstructor;
import com.vantruong.notification.client.UserClient;
import com.vantruong.notification.constant.CommonConstant;
import com.vantruong.notification.dto.OrderDto;
import com.vantruong.notification.job.SendMailJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

import static com.vantruong.notification.constant.CommonConstant.VERIFY_EMAIL;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
  private final TemplateEngine templateEngine;
  private final JavaMailSender javaMailSender;
  private final UserClient userClient;

  @Value("${spring.mail.username}")
  private String systemEmail;

  private void jobSendMail(String from, String to, String subject, String htmlBody, JavaMailSender javaMailSender) {
    //  check existed user
    userClient.existedUserByEmail(to);
    Thread job = new SendMailJob(from, to, subject, htmlBody, javaMailSender);
    job.start();
  }

  @Override
  public void sendVerificationEmail(SendMailVerifyUserRequest request) {
    Context context = new Context();

    String urlVerification = UriComponentsBuilder
            .fromHttpUrl(CommonConstant.BASE_URL_CLIENT)
            .path(VERIFY_EMAIL)
            .queryParam("token", request.getToken())
            .toUriString();

    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("name", request.getName());
    templateModel.put("url", urlVerification);

    context.setVariables(templateModel);

    String subject = "Verify email";
    String htmlBody = templateEngine.process(EmailConstant.VERIFY_EMAIL, context);

    jobSendMail(systemEmail, request.getEmail(), subject, htmlBody, javaMailSender);
  }

  @Override
  public void sendResetPassword(SendMailVerifyUserRequest request) {
    Context context = new Context();

    String urlResetPassword = UriComponentsBuilder
            .fromHttpUrl(CommonConstant.BASE_URL_CLIENT)
            .path(CommonConstant.RESET_PASSWORD)
            .queryParam("token", request.getToken())
            .toUriString();

    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("name", request.getName());
    templateModel.put("url", urlResetPassword);

    context.setVariables(templateModel);

    String subject = "Reset password";
    String htmlBody = templateEngine.process(EmailConstant.RESET_PASSWORD, context);

    jobSendMail(systemEmail, request.getEmail(), subject, htmlBody, javaMailSender);
  }

  @Override
  public void confirmOrder(OrderDto orderDto) {
    Context context = new Context();

    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("order", orderDto);
    context.setVariables(templateModel);

    String subject = "Xác nhận đơn đặt hàng";
    String htmlBody = templateEngine.process(EmailConstant.CONFIRM_ORDER, context);

    jobSendMail(systemEmail, orderDto.getEmail(), subject, htmlBody, javaMailSender);
  }

}
