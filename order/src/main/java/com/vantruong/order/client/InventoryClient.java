package com.vantruong.order.client;

import com.vantruong.order.common.CommonResponse;
import com.vantruong.order.viewmodel.ProductQuantityCheckVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.vantruong.order.constant.InternalApiEndpoint.*;

@FeignClient(name = "inventory-service", url = INVENTORY_SERVICE_URL + INTERNAL)
public interface InventoryClient {

  @PostMapping(CHECK_LIST_PRODUCT_QUANTITY)
  CommonResponse<Boolean> checkListProductQuantity(@RequestBody List<ProductQuantityCheckVm> request);
}
