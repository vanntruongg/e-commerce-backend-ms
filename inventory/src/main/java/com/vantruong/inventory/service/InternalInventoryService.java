package com.vantruong.inventory.service;

import com.vantruong.inventory.entity.Inventory;
import com.vantruong.inventory.entity.SizeQuantity;
import com.vantruong.inventory.repository.InventoryRepository;
import com.vantruong.inventory.viewmodel.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternalInventoryService {
  private final InventoryRepository inventoryRepository;
  private final InventoryService inventoryService;

  public Boolean createInventory(InventoryPostVm inventoryPost) {
    try {
      List<SizeQuantity> sizeQuantityList = inventoryPost.sizeQuantityVms().stream()
              .map(sizeQuantityDto -> SizeQuantity.builder()
                      .size(sizeQuantityDto.size())
                      .quantity(sizeQuantityDto.quantity())
                      .build()
              )
              .toList();

      Inventory inventory = Inventory.builder()
              .productId(inventoryPost.productId())
              .sizes(sizeQuantityList)
              .build();
      inventoryRepository.save(inventory);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * checks if the quantities of all products in the list meet the requested quantities.
   *
   * @param requests a list of CheckProductQuantityRequest objects containing product IDs, sizes, and requested quantities.
   * @return true if all products have sufficient quantities, otherwise false.
   */
  public Boolean checkListProductQuantityById(List<ProductQuantityCheckVm> requests) {
    return requests.stream().noneMatch(request -> {
      Integer availableQuantity = inventoryService.getQuantityByProductIdAndSize(request.productId(), request.size());
      return availableQuantity != null && availableQuantity < request.quantity();
    });
  }

  public Integer checkProductQuantity(ProductCheckVm request) {
    return inventoryService.getQuantityByProductIdAndSize(request.productId(), request.size());
  }

  public ProductInventoryVm getAllInventoryByProductIds(ProductListInventoryCheckVm request) {

    Map<Long, Inventory> inventorieMap = inventoryRepository.findAll().stream()
            .collect(Collectors.toMap(Inventory::getProductId, inventory -> inventory));

    Map<Long, List<SizeQuantityVm>> responseMap = request.productIds().stream()
            .collect(Collectors.toMap(productId -> productId,
                    productId -> {
                      Inventory inventory = inventorieMap.get(productId);

                      return inventory.getSizes().stream()
                              .map(sizeQuantity -> new SizeQuantityVm(sizeQuantity.getSize(), sizeQuantity.getQuantity()))
                              .toList();
                    }
            ));

    return new ProductInventoryVm(responseMap);
  }

  public List<SizeQuantityVm> getInventoryByProductId(Long productId) {
    return inventoryService.findByProductId(productId);
  }
}
