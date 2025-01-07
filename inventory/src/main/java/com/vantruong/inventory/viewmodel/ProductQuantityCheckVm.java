package com.vantruong.inventory.viewmodel;

public record ProductQuantityCheckVm(
        Long productId,
        String size,
        Integer quantity
) {
}
