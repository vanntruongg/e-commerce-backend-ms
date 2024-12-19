package com.vantruong.product.dto;

public record CategoryPut(
        Long categoryId,
        String name,
        String imageUrl,
        Long parentCategoryId,
        boolean isTopLevel
) {
}
