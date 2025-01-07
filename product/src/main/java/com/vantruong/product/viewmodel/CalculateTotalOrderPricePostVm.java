package com.vantruong.product.viewmodel;

import java.util.List;

public record CalculateTotalOrderPricePostVm(
        List<ProductQuantityVm> productQuantityVms
) {
}
