package com.vantruong.inventory.dto;

import com.vantruong.common.dto.inventory.SizeQuantityDto;
import com.vantruong.inventory.entity.SizeQuantity;

import java.util.List;

public record InventoryPut(
        Long productId,
        List<SizeQuantity> sizeQuantity
) {
}
