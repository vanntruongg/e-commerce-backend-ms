package com.vantruong.common.dto.product;

public record ProductSoldResponse(
        Long productId,
        int totalQuantitySold
) {
}
