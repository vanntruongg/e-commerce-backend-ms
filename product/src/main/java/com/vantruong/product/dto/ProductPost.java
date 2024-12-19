package com.vantruong.product.dto;

import com.vantruong.common.dto.inventory.SizeQuantityDto;

import java.util.List;

public record ProductPost(
        Long id,
        String name,
        double price,
        String material,
        String style,
        String imageUrl,
        String description,
//        List<String> imageUrls,
        Long categoryId,
        List<SizeQuantityDto> stock
) {
}
