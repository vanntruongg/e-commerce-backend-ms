package com.vantruong.product.client;

import com.vantruong.product.common.CommonResponse;
import com.vantruong.product.viewmodel.InventoryPostVm;
import com.vantruong.product.viewmodel.ProductInventoryVm;
import com.vantruong.product.viewmodel.ProductListInventoryCheckVm;
import com.vantruong.product.viewmodel.SizeQuantityVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.vantruong.product.constant.InternalApiEndpoint.*;


@FeignClient(name = "inventory-service", url = INVENTORY_SERVICE_URL + INTERNAL)
public interface InventoryClient {

  @PostMapping(GET_ALL_BY_PRODUCT_IDS)
  CommonResponse<ProductInventoryVm> getAllInventoryByProductIds(@RequestBody ProductListInventoryCheckVm request);

  @GetMapping(GET_BY_PRODUCT_ID)
  CommonResponse<List<SizeQuantityVm>> getInventoryByProductId(@PathVariable("id") Long productId);

  @PostMapping(CREATE_INVENTORY)
  CommonResponse<Boolean> createInventory(@RequestBody InventoryPostVm inventoryPost);
}
