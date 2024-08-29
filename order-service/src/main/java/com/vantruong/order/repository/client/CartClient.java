package com.vantruong.order.repository.client;

import com.vantruong.common.dto.request.DeleteCartItemsRequest;
import com.vantruong.order.common.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.vantruong.common.constant.InternalApiEndpoint.CART_DELETE_ITEMS;
import static com.vantruong.common.constant.InternalApiEndpoint.CART_SERVICE_URL;

@FeignClient(name = "cart-service", url = CART_SERVICE_URL)
public interface CartClient {
  @DeleteMapping(CART_DELETE_ITEMS)
  CommonResponse<Object> removeItemsFromCart(@RequestBody DeleteCartItemsRequest request);
}