package com.vantruong.inventory.service.impl;

import com.vantruong.common.dto.SizeQuantityDto;
import com.vantruong.common.dto.request.ProductInventoryRequest;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.dto.response.InventoryResponse;
import com.vantruong.common.dto.response.ProductInventoryResponse;
import com.vantruong.inventory.entity.Inventory;
import com.vantruong.inventory.repository.InventoryRepository;
import com.vantruong.inventory.service.InternalInventoryService;
import com.vantruong.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternalInventoryServiceImpl implements InternalInventoryService {
  private final InventoryRepository inventoryRepository;
  private final InventoryService inventoryService;

  /**
   * checks if the quantities of all products in the list meet the requested quantities.
   *
   * @param requests a list of CheckProductQuantityRequest objects containing product IDs, sizes, and requested quantities.
   * @return true if all products have sufficient quantities, otherwise false.
   */
  @Override
  public Boolean checkListProductQuantityById(List<ProductQuantityRequest> requests) {
    return requests.stream().noneMatch(request -> {
      Integer availableQuantity = inventoryService.getQuantityByProductIdAndSize(request.getProductId(), request.getSize());
      return availableQuantity != null && availableQuantity < request.getQuantity();
    });
  }

  @Override
  public Integer checkProductQuantity(ProductQuantityRequest request) {
    return inventoryService.getQuantityByProductIdAndSize(request.getProductId(), request.getSize());
  }

  @Override
  public ProductInventoryResponse getAllInventoryByProductIds(ProductInventoryRequest request) {

    Map<Integer, Inventory> inventorieMap = inventoryRepository.findAll().stream()
            .collect(Collectors.toMap(Inventory::getProductId, inventory -> inventory));

    Map<Integer, List<SizeQuantityDto>> responseMap = request.getProductIds().stream()
            .collect(Collectors.toMap(productId -> productId,
                    productId -> {
                      Inventory inventory = inventorieMap.get(productId);

                      return inventory.getSizes().stream()
                              .map(sizeQuantity -> SizeQuantityDto.builder()
                                      .size(sizeQuantity.getSize().name())
                                      .quantity(sizeQuantity.getQuantity())
                                      .build())
                              .toList();
                    }
            ));

    return ProductInventoryResponse.builder()
            .productInventoryResponse(responseMap)
            .build();
  }

  @Override
  public List<SizeQuantityDto> getInventoryByProductId(Integer productId) {
    return inventoryService.findByProductId(productId);
  }
}
