package com.vantruong.inventory.service;

import com.vantruong.common.dto.inventory.SizeQuantityDto;
import com.vantruong.common.dto.request.ProductInventoryRequest;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.dto.response.ProductInventoryResponse;
import com.vantruong.inventory.entity.Inventory;
import com.vantruong.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InternalInventoryService {
  private final InventoryRepository inventoryRepository;
  private final InventoryService inventoryService;

  public InternalInventoryService(InventoryRepository inventoryRepository, InventoryService inventoryService) {
    this.inventoryRepository = inventoryRepository;
    this.inventoryService = inventoryService;
  }

  /**
   * checks if the quantities of all products in the list meet the requested quantities.
   *
   * @param requests a list of CheckProductQuantityRequest objects containing product IDs, sizes, and requested quantities.
   * @return true if all products have sufficient quantities, otherwise false.
   */
  public Boolean checkListProductQuantityById(List<ProductQuantityRequest> requests) {
    return requests.stream().noneMatch(request -> {
      Integer availableQuantity = inventoryService.getQuantityByProductIdAndSize(request.getProductId(), request.getSize());
      return availableQuantity != null && availableQuantity < request.getQuantity();
    });
  }

  public Integer checkProductQuantity(ProductQuantityRequest request) {
    return inventoryService.getQuantityByProductIdAndSize(request.getProductId(), request.getSize());
  }

  public ProductInventoryResponse getAllInventoryByProductIds(ProductInventoryRequest request) {

    Map<Long, Inventory> inventorieMap = inventoryRepository.findAll().stream()
            .collect(Collectors.toMap(Inventory::getProductId, inventory -> inventory));

    Map<Long, List<SizeQuantityDto>> responseMap = request.getProductIds().stream()
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

  public List<SizeQuantityDto> getInventoryByProductId(Long productId) {
    return inventoryService.findByProductId(productId);
  }
}
