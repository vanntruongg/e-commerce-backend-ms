package com.vantruong.inventory.service;

import com.vantruong.common.dto.inventory.SizeQuantityDto;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.exception.Constant;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.inventory.constant.MessageConstant;
import com.vantruong.inventory.dto.InventoryPut;
import com.vantruong.inventory.entity.Inventory;
import com.vantruong.inventory.entity.SizeQuantity;
import com.vantruong.inventory.repository.InventoryRepository;
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
            .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));
  }

  public List<SizeQuantityDto> findByProductId(Long productId) {
    Inventory inventory = findInventoryByProductId(productId);
    return convertToListSizeQuantityResponse(inventory.getSizes());
  }

  public Integer getQuantityByProductIdAndSize(Long productId, String size) {
    Inventory inventory = findInventoryByProductId(productId);
    SizeQuantity sizeQuantity = inventory.getSizes().stream()
            .filter(sizeQuantity1 -> sizeQuantity1.getSize().equals(size))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));
    return sizeQuantity.getQuantity();
  }

  public SizeQuantityDto findByProductIdAndSize(String size, Long id) {
    Integer quantity = getQuantityByProductIdAndSize(id, size);

    return SizeQuantityDto.builder().size(size).quantity(quantity).build();
  }


  public Boolean updateQuantityAndCompensate(List<ProductQuantityRequest> requests, boolean compensate) {
    for (ProductQuantityRequest request : requests) {
      Inventory inventory = findInventoryByProductId(request.getProductId());

      Optional<SizeQuantity> matchingSize = findSizeQuantity(inventory, request.getSize());

      if (matchingSize.isEmpty()) return false;

      SizeQuantity sizeQuantity = matchingSize.get();

      if (compensate) {
        sizeQuantity.setQuantity(sizeQuantity.getQuantity() + request.getQuantity());
      } else {
        if (sizeQuantity.getQuantity() < request.getQuantity()) {
          return false;
        }
        sizeQuantity.setQuantity(sizeQuantity.getQuantity() - request.getQuantity());
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

  private List<SizeQuantityDto> convertToListSizeQuantityResponse(List<SizeQuantity> sizeQuantities) {
    return sizeQuantities.stream()
            .map(sizeQuantity -> SizeQuantityDto.builder()
                    .size(sizeQuantity.getSize())
                    .quantity(sizeQuantity.getQuantity())
                    .build())
            .collect(Collectors.toList());
  }

  public Boolean updateInventory(InventoryPut inventoryPut) {
    Inventory inventory = inventoryRepository.findInventoryByProductId(inventoryPut.productId())
            .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));

    inventory.setSizes(inventoryPut.sizeQuantity());
    inventoryRepository.save(inventory);
    return true;
  }
}
