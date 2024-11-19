package com.vantruong.common.event;

public record CancelOrderItem(
        Long productId,
        int quantity,
        String productSize

) {
}
