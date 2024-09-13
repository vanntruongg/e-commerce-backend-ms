package com.vantruong.product.dto;

import com.vantruong.common.dto.response.CategoryResponse;
import com.vantruong.common.dto.response.ProductImageResponse;

import java.util.List;

public record ProductResponse(Long id,
                              String name,
                              Double price,
                              String material,
                              String style,
                              CategoryResponse category,
                              List<ProductImageResponse> images
) {
}


