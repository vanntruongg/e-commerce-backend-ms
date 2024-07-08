package notificationservice.service;


import notificationservice.dto.OrderDto;
import notificationservice.dto.SendMailVerifyUserRequest;

public interface MailService {

  void sendVerificationEmail(SendMailVerifyUserRequest request);

  void sendResetPassword(SendMailVerifyUserRequest request);

  void confirmOrder(OrderDto orderDto);
}
