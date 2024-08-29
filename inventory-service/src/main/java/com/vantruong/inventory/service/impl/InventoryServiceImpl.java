package com.vantruong.inventory.service.impl;

import com.vantruong.common.dto.SizeQuantityDto;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.common.exception.ErrorCode;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.inventory.constant.MessageConstant;
import com.vantruong.inventory.entity.Inventory;
import com.vantruong.inventory.entity.SizeQuantity;
import com.vantruong.inventory.repository.InventoryRepository;
import com.vantruong.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
  private final InventoryRepository inventoryRepository;

  private Inventory findInventoryByProductId(Integer productId) {
    return inventoryRepository.findInventoryByProductId(productId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));
  }

  @Override
  public List<SizeQuantityDto> findByProductId(Integer productId) {
    Inventory inventory = findInventoryByProductId(productId);
    return convertToListSizeQuantityResponse(inventory.getSizes());
  }

  @Override
  public Integer getQuantityByProductIdAndSize(int productId, String size) {
    Inventory inventory = findInventoryByProductId(productId);
    SizeQuantity sizeQuantity = inventory.getSizes().stream()
            .filter(sizeQuantity1 -> sizeQuantity1.getSize().name().equals(size))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));
    return sizeQuantity.getQuantity();
  }

  @Override
  public SizeQuantityDto findByProductIdAndSize(String size, Integer id) {
    Integer quantity = getQuantityByProductIdAndSize(id, size);

    return SizeQuantityDto.builder().size(size).quantity(quantity).build();
  }


  @Override
  public Boolean updateQuantity(List<ProductQuantityRequest> requests, boolean compensate) {
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
            .filter(sq -> sq.getSize().name().equals(size))
            .findFirst();
  }

  private List<SizeQuantityDto> convertToListSizeQuantityResponse(List<SizeQuantity> sizeQuantities) {
    return sizeQuantities.stream()
            .map(sizeQuantity -> SizeQuantityDto.builder()
                    .size(sizeQuantity.getSize().name())
                    .quantity(sizeQuantity.getQuantity())
                    .build())
            .collect(Collectors.toList());
  }
}
