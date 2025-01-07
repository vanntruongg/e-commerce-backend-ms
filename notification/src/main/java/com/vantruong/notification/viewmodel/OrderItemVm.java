package com.vantruong.notification.viewmodel;

public record OrderItemVm(
        int orderDetailId,
        Long productId,
        int quantity,
        String productName,
        Double productPrice,
        String productImage,
        String productSize
) {
}
