package com.vantruong.rating.dto;

import lombok.Builder;

@Builder
public record RatingPost(
        String content,
        int star,
        Long productId,
        String productName
) {
}
