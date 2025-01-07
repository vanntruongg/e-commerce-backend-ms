package com.vantruong.cart.client;

import com.vantruong.cart.common.CommonResponse;
import com.vantruong.cart.viewmodel.ProductCheckVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.vantruong.cart.constant.InternalApiEndpoint.CHECK_PRODUCT_QUANTITY;
import static com.vantruong.cart.constant.InternalApiEndpoint.INVENTORY_SERVICE_URL;

@FeignClient(name = "inventory-service", url = INVENTORY_SERVICE_URL)
public interface InventoryClient {

  @PostMapping(CHECK_PRODUCT_QUANTITY)
  CommonResponse<Integer> checkProductQuantityById(@RequestBody ProductCheckVm request);
}
