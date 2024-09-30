package com.vantruong.product.service;

import com.vantruong.common.dto.inventory.InventoryPost;
import com.vantruong.common.dto.inventory.SizeQuantityDto;
import com.vantruong.common.dto.request.ProductInventoryRequest;
import com.vantruong.common.dto.response.ProductInventoryResponse;
import com.vantruong.product.client.InventoryClient;
import com.vantruong.product.common.CommonResponse;
import com.vantruong.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
  private final InventoryClient inventoryClient;

  public ProductInventoryResponse getInventoryByProductIds(List<Product> productList) {

    List<Long> productIds = productList.stream()
            .map(Product::getId)
            .toList();

    ProductInventoryRequest request = ProductInventoryRequest.builder()
            .productIds(productIds)
            .build();
    CommonResponse<ProductInventoryResponse> response = inventoryClient.getAllInventoryByProductIds(request);

    return response.getData();
  }

  public Boolean createInventory(Long productId, List<SizeQuantityDto> sizeQuantityDtoList) {
    InventoryPost inventoryPost = new InventoryPost(productId, sizeQuantityDtoList);
    var response = inventoryClient.createInventory(inventoryPost);
    return response.getData();
  }
}
