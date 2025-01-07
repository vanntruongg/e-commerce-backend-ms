package com.vantruong.product.dto;

import com.vantruong.product.viewmodel.CategoryVm;
import com.vantruong.product.viewmodel.SizeQuantityVm;

import java.util.List;

public record ProductResponse(Long id,
                              String name,
                              Double price,
                              String material,
                              String style,
                              CategoryVm category,
                              String imageUrl,
                              String description,
//                              List<ProductImageResponse> images,
                              List<SizeQuantityVm> sizeQuantity
) {
}


