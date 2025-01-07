package com.vantruong.order.viewmodel;

public record OrderItemVm(
        Long productId,
        int quantity,
        String productName,
        Double productPrice,
        String productImage,
        String size
) {
}
