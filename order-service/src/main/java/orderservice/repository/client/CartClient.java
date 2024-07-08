package orderservice.repository.client;

import orderservice.common.CommonResponse;
import orderservice.entity.dto.internal.RemoveItemsCartRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static orderservice.constant.InternalApiEndpoint.CART_DELETE_ITEMS;
import static orderservice.constant.InternalApiEndpoint.CART_SERVICE_URL;

@FeignClient(name = "cart-service", url = CART_SERVICE_URL)
public interface CartClient {
  @DeleteMapping(CART_DELETE_ITEMS)
  CommonResponse<Object> removeItemsFromCart(@RequestBody RemoveItemsCartRequest removeItemsCartRequest);
}