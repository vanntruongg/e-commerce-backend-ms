package com.vantruong.product.viewmodel;

import java.util.List;
import java.util.Map;

public record ProductInventoryVm(
        Map<Long, List<SizeQuantityVm>> productInventoryVm
) {
}
