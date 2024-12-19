package com.vantruong.rating.dto;

import java.util.List;

public record RatingListDto(
        List<RatingDto> ratingList,
        long totalElements,
        int totalPages
) {
}
