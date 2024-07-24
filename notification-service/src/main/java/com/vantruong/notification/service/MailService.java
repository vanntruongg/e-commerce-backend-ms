package com.vantruong.notification.service;


import com.vantruong.notification.dto.SendMailVerifyUserRequest;
import com.vantruong.notification.dto.OrderDto;

public interface MailService {

  void sendVerificationEmail(SendMailVerifyUserRequest request);

  void sendResetPassword(SendMailVerifyUserRequest request);

  void confirmOrder(OrderDto orderDto);
}
