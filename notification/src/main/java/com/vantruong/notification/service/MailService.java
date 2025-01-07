package com.vantruong.notification.service;

import com.vantruong.notification.client.UserClient;
import com.vantruong.notification.constant.CommonConstant;
import com.vantruong.notification.constant.EmailConstant;
import com.vantruong.notification.viewmodel.OrderVm;
import com.vantruong.notification.viewmodel.SendMailVerifyUserRequest;
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
public class MailService {
  private final TemplateEngine templateEngine;
  private final JavaMailSender javaMailSender;
  private final UserClient userClient;

  @Value("${spring.mail.username}")
  private String systemEmail;

  public MailService(TemplateEngine templateEngine, JavaMailSender javaMailSender, UserClient userClient) {
    this.templateEngine = templateEngine;
    this.javaMailSender = javaMailSender;
    this.userClient = userClient;
  }

  private void jobSendMail(String from, String to, String subject, String htmlBody, JavaMailSender javaMailSender) {
    //  check existed user
    userClient.existedUserByEmail(to);
    Thread job = new SendMailJob(from, to, subject, htmlBody, javaMailSender);
    job.start();
  }

  public void sendVerificationEmail(SendMailVerifyUserRequest request) {
    Context context = new Context();

    String urlVerification = UriComponentsBuilder
            .fromHttpUrl(CommonConstant.BASE_URL_CLIENT)
            .path(VERIFY_EMAIL)
            .queryParam("token", request.token())
            .toUriString();

    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("name", request.name());
    templateModel.put("url", urlVerification);

    context.setVariables(templateModel);

    String subject = "Verify email";
    String htmlBody = templateEngine.process(EmailConstant.VERIFY_EMAIL, context);

    jobSendMail(systemEmail, request.email(), subject, htmlBody, javaMailSender);
  }

  public void sendResetPassword(SendMailVerifyUserRequest request) {
    Context context = new Context();

    String urlResetPassword = UriComponentsBuilder
            .fromHttpUrl(CommonConstant.BASE_URL_CLIENT)
            .path(CommonConstant.RESET_PASSWORD)
            .queryParam("token", request.token())
            .toUriString();

    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("name", request.name());
    templateModel.put("url", urlResetPassword);

    context.setVariables(templateModel);

    String subject = "Reset password";
    String htmlBody = templateEngine.process(EmailConstant.RESET_PASSWORD, context);

    jobSendMail(systemEmail, request.email(), subject, htmlBody, javaMailSender);
  }

  public void confirmOrder(OrderVm orderVm) {
    Context context = new Context();

    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("order", orderVm);
    context.setVariables(templateModel);

    String subject = "Xác nhận đơn đặt hàng";
    String htmlBody = templateEngine.process(EmailConstant.CONFIRM_ORDER, context);

    jobSendMail(systemEmail, orderVm.email(), subject, htmlBody, javaMailSender);
  }

}
