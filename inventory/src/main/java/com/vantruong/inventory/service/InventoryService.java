package com.vantruong.inventory.service;

import com.vantruong.inventory.constant.MessageConstant;
import com.vantruong.inventory.dto.InventoryPut;
import com.vantruong.inventory.entity.Inventory;
import com.vantruong.inventory.entity.SizeQuantity;
import com.vantruong.inventory.exception.ErrorCode;
import com.vantruong.inventory.exception.NotFoundException;
import com.vantruong.inventory.repository.InventoryRepository;
import com.vantruong.inventory.viewmodel.ProductQuantityCheckVm;
import com.vantruong.inventory.viewmodel.SizeQuantityVm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {
  private final InventoryRepository inventoryRepository;

  public InventoryService(InventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  private Inventory findInventoryByProductId(Long productId) {
    return inventoryRepository.findInventoryByProductId(productId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));
  }

  public List<SizeQuantityVm> findByProductId(Long productId) {
    Inventory inventory = findInventoryByProductId(productId);
    return convertToListSizeQuantityResponse(inventory.getSizes());
  }

  public Integer getQuantityByProductIdAndSize(Long productId, String size) {
    Inventory inventory = findInventoryByProductId(productId);
    SizeQuantity sizeQuantity = inventory.getSizes().stream()
            .filter(sizeQuantity1 -> sizeQuantity1.getSize().equals(size))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));
    return sizeQuantity.getQuantity();
  }

  public SizeQuantityVm findByProductIdAndSize(String size, Long id) {
    Integer quantity = getQuantityByProductIdAndSize(id, size);

    return new SizeQuantityVm(size, quantity);
  }


  public Boolean updateQuantityAndCompensate(List<ProductQuantityCheckVm> requests, boolean compensate) {
    for (ProductQuantityCheckVm request : requests) {
      Inventory inventory = findInventoryByProductId(request.productId());

      Optional<SizeQuantity> matchingSize = findSizeQuantity(inventory, request.size());

      if (matchingSize.isEmpty()) return false;

      SizeQuantity sizeQuantity = matchingSize.get();

      if (compensate) {
        sizeQuantity.setQuantity(sizeQuantity.getQuantity() + request.quantity());
      } else {
        if (sizeQuantity.getQuantity() < request.quantity()) {
          return false;
        }
        sizeQuantity.setQuantity(sizeQuantity.getQuantity() - request.quantity());
      }

      inventoryRepository.save(inventory);
    }
    return true;
  }

  private Optional<SizeQuantity> findSizeQuantity(Inventory inventory, String size) {
    return inventory.getSizes().stream()
            .filter(sq -> sq.getSize().equals(size))
            .findFirst();
  }

  private List<SizeQuantityVm> convertToListSizeQuantityResponse(List<SizeQuantity> sizeQuantities) {
    return sizeQuantities.stream()
            .map(sizeQuantity -> new SizeQuantityVm(sizeQuantity.getSize(), sizeQuantity.getQuantity()))
            .collect(Collectors.toList());
  }

  public Boolean updateInventory(InventoryPut inventoryPut) {
    Inventory inventory = inventoryRepository.findInventoryByProductId(inventoryPut.productId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));

    inventory.setSizes(inventoryPut.sizeQuantity());
    inventoryRepository.save(inventory);
    return true;
  }
}
