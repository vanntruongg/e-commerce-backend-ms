package com.vantruong.product.viewmodel;

import java.util.List;

public record InventoryPostVm(
        Long productId,
        List<SizeQuantityVm> sizeQuantityVms
) {
}
