package com.vantruong.product.dto;

public record CategoryPost(
        String name,
        String imageUrl,
        Long parentCategoryId
) {
}
