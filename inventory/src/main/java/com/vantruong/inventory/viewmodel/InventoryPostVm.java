package com.vantruong.inventory.viewmodel;

import java.util.List;

public record InventoryPostVm(
        Long productId,
        List<SizeQuantityVm> sizeQuantityVms
) {
}
