package com.vantruong.product.dto;

import com.vantruong.product.viewmodel.SizeQuantityVm;

import java.util.List;

public record ProductPost(
        Long id,
        String name,
        double price,
        String material,
        String style,
        String imageUrl,
        String description,
        Long categoryId,
        List<SizeQuantityVm> stock
) {
}
