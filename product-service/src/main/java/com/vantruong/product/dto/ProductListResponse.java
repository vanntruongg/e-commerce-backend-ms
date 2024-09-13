package com.vantruong.product.dto;

import java.util.List;

public record ProductListResponse(
        List<ProductResponse> productContent,
        int pageNo,
        int pageSize,
        int totalElements,
        int totalPages,
        boolean isLast
) {
}