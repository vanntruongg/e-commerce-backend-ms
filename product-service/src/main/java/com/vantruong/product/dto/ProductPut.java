package com.vantruong.product.dto;

import java.util.List;

public record ProductPut(
        Long id,
        String name,
        double price,
        String material,
        String style,
        List<String> imageUrl,
        Long categoryId
) {
}
