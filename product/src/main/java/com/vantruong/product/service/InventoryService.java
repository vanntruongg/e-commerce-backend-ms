package com.vantruong.product.service;

import com.vantruong.product.client.InventoryClient;
import com.vantruong.product.common.CommonResponse;
import com.vantruong.product.entity.Product;
import com.vantruong.product.viewmodel.InventoryPostVm;
import com.vantruong.product.viewmodel.ProductInventoryVm;
import com.vantruong.product.viewmodel.ProductListInventoryCheckVm;
import com.vantruong.product.viewmodel.SizeQuantityVm;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService extends AbstractCircuitBreakerFallbackHandler {
  private final InventoryClient inventoryClient;

  @Retry(name = "restApi")
  @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "getInventoryFallback")
  public ProductInventoryVm getInventoryByProductIds(List<Product> productList) {

    List<Long> productIds = productList.stream()
            .map(Product::getId)
            .toList();

    ProductListInventoryCheckVm request = new ProductListInventoryCheckVm(productIds);
    CommonResponse<ProductInventoryVm> response = inventoryClient.getAllInventoryByProductIds(request);

    return response.getData();
  }

  protected ProductInventoryVm getInventoryFallback(Throwable throwable) throws Throwable {
    log.error("Fallback method called due to error: {}", throwable.getMessage());
    return handleTypedFallback(throwable);
  }

  @Retry(name = "restApi")
  @CircuitBreaker(name = "restCircuitBreaker", fallbackMethod = "createInventoryFallback")
  public Boolean createInventory(Long productId, List<SizeQuantityVm> sizeQuantityDtoList) {
    InventoryPostVm inventoryPost = new InventoryPostVm(productId, sizeQuantityDtoList);
    inventoryClient.createInventory(inventoryPost);
    return true;
  }

  protected Boolean createInventoryFallback(Throwable throwable) throws Throwable {
    return handleTypedFallback(throwable);
  }
}
