package com.vantruong.order.viewmodel;

import java.util.List;

public record CalculateTotalOrderPricePostVm(
        List<ProductQuantityVm> productQuantityVms
) {
}
