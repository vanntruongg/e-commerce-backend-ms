package com.vantruong.cart.viewmodel;

public record ProductVm(
        Long id,
        String name,
        Double price,
        String material,
        String style,
        String imageUrl,
        CategoryVm categoryVm
) {
}
