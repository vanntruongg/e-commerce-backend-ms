package com.vantruong.order.client;

import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.order.common.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.vantruong.common.constant.InternalApiEndpoint.CHECK_LIST_PRODUCT_QUANTITY;
import static com.vantruong.common.constant.InternalApiEndpoint.INVENTORY_SERVICE_URL;


@FeignClient(name = "inventory-service", url = INVENTORY_SERVICE_URL)
public interface InventoryClient {

  @PostMapping(CHECK_LIST_PRODUCT_QUANTITY)
  CommonResponse<Boolean> checkListProductQuantity(@RequestBody List<ProductQuantityRequest> request);
}
