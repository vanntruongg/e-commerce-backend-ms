package orderservice.repository.client;

import orderservice.common.CommonResponse;
import orderservice.dto.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import static orderservice.constant.InternalApiEndpoint.*;

@FeignClient(name = "address-data-service", url = USER_ADDRESS_SERVICE_URL)
public interface UserAddressClient {
  @GetMapping(GET_ADDRESS_BY_ID)
  CommonResponse<UserAddress> getUserAddressById(@PathVariable("id") Integer addressId);
}
