package orderservice.repository.client;

import orderservice.entity.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static orderservice.constant.InternalApiEndpoint.MAIL_SERVICE_URL;
import static orderservice.constant.InternalApiEndpoint.MAIL_CONFIRM_ORDER;

@FeignClient(name = "mail-service", url = MAIL_SERVICE_URL)
public interface MailClient {
  @PostMapping(MAIL_CONFIRM_ORDER)
  void sendMailConfirmOrder(@RequestBody OrderDto orderDto);
}
