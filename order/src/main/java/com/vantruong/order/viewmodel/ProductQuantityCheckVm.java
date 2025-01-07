package com.vantruong.order.viewmodel;

public record ProductQuantityCheckVm(
        Long productId,
        String size,
        Integer quantity
) {
}
