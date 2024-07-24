package com.vantruong.order.repository.client;

import com.vantruong.order.common.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import static com.vantruong.order.constant.InternalApiEndpoint.PRODUCT_SERVICE_URL;
import static com.vantruong.order.constant.InternalApiEndpoint.PRODUCT_UPDATE_QUANTITY;

@FeignClient(name = "product-service", url = PRODUCT_SERVICE_URL)
public interface ProductClient {
  @PostMapping(PRODUCT_UPDATE_QUANTITY)
  CommonResponse<Object> updateProductQuantity(@RequestBody Map<Integer, Integer> stockUpdate);
}
