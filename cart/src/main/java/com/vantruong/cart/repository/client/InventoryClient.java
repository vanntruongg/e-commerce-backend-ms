package com.vantruong.cart.repository.client;

import com.vantruong.cart.common.CommonResponse;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.common.constant.InternalApiEndpoint.CHECK_PRODUCT_QUANTITY;
import static com.vantruong.common.constant.InternalApiEndpoint.INVENTORY_SERVICE_URL;

@FeignClient(name = "inventory-service", url = INVENTORY_SERVICE_URL)
public interface InventoryClient {

  @PostMapping(CHECK_PRODUCT_QUANTITY)
  CommonResponse<Integer> checkProductQuantityById(@RequestBody ProductQuantityRequest request);
}
