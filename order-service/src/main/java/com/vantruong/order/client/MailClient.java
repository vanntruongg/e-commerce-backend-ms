//package com.vantruong.order.repository.client;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import static com.vantruong.common.constant.InternalApiEndpoint.MAIL_CONFIRM_ORDER;
//import static com.vantruong.common.constant.InternalApiEndpoint.MAIL_SERVICE_URL;
//
//@FeignClient(name = "mail-service", url = MAIL_SERVICE_URL)
//public interface MailClient {
//  @PostMapping(MAIL_CONFIRM_ORDER)
//  void sendMailConfirmOrder(@RequestBody OrderSendMailRequest orderDto);
//}

