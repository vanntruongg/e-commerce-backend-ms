package com.vantruong.cart.viewmodel;

public record CategoryVm(
        Long id,
        String name,
        CategoryVm parentCategory
) {
}
