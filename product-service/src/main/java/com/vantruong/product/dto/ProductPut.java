package com.vantruong.product.dto;

public record ProductPut(
        Long id,
        String name,
        double price,
        String material,
        String style,
//        List<String> imageUrls,
        String imageUrl,
        String description,
        Long categoryId
) {
}
