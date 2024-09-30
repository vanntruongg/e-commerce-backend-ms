package com.vantruong.order.dto;


import lombok.Builder;

import java.util.List;

@Builder
public record OrderItemDto(
        Long productId,
        String productName,
        double productPrice,
        String productImage,
        List<SizeQuantity> sizeQuantityList
) {
}
