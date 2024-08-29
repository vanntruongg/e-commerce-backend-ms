package com.vantruong.inventory.service;

import com.vantruong.common.dto.SizeQuantityDto;
import com.vantruong.common.dto.request.ProductInventoryRequest;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.dto.response.ProductInventoryResponse;

import java.util.List;

public interface InternalInventoryService {
  Boolean checkListProductQuantityById(List<ProductQuantityRequest> requests);

  Integer checkProductQuantity(ProductQuantityRequest request);

  ProductInventoryResponse getAllInventoryByProductIds(ProductInventoryRequest request);

  List<SizeQuantityDto> getInventoryByProductId(Integer productId);
}
