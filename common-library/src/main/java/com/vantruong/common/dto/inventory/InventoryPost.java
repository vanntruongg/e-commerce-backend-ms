package com.vantruong.common.dto.inventory;

import java.util.List;

public record InventoryPost(
        Long productId,
        List<SizeQuantityDto> sizeQuantityDtoList
) {
}
