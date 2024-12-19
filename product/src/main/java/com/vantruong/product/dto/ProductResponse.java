package com.vantruong.product.dto;

import com.vantruong.common.dto.inventory.SizeQuantityDto;
import com.vantruong.common.dto.response.CategoryResponse;
import com.vantruong.common.dto.response.ProductImageResponse;

import java.util.List;

public record ProductResponse(Long id,
                              String name,
                              Double price,
                              String material,
                              String style,
                              CategoryResponse category,
                              String imageUrl,
                              String description,
//                              List<ProductImageResponse> images,
                              List<SizeQuantityDto> sizeQuantity
) {
}


