package com.vantruong.product.repository.client;

import com.vantruong.common.dto.inventory.SizeQuantityDto;
import com.vantruong.common.dto.request.ProductInventoryRequest;
import com.vantruong.common.dto.response.ProductInventoryResponse;
import com.vantruong.product.common.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.vantruong.common.constant.InternalApiEndpoint.*;


@FeignClient(name = "inventory-service", url = INVENTORY_SERVICE_URL)
public interface InventoryClient {

  @PostMapping(GET_ALL_BY_PRODUCT_IDS)
  CommonResponse<ProductInventoryResponse> getAllInventoryByProductIds(@RequestBody ProductInventoryRequest request);

  @GetMapping(GET_BY_PRODUCT_ID)
  CommonResponse<List<SizeQuantityDto>> getInventoryByProductId(@PathVariable("id") Long productId);
}
