package com.vantruong.order.dto;

import lombok.Builder;

@Builder
public record SizeQuantity(
        String size,
        int quantity
) {
}
