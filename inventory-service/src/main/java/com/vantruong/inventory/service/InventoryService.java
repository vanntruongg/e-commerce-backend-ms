package com.vantruong.inventory.service;

import com.vantruong.common.dto.SizeQuantityDto;
import com.vantruong.common.dto.request.ProductQuantityRequest;

import java.util.List;

public interface InventoryService {

  List<SizeQuantityDto> findByProductId(Integer productId);

  SizeQuantityDto findByProductIdAndSize(String size, Integer id);

  Boolean updateQuantity(List<ProductQuantityRequest> requests, boolean compensate);
  Integer getQuantityByProductIdAndSize(int productId, String size);
}
