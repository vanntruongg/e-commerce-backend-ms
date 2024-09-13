package com.vantruong.rating.dto;

import com.vantruong.common.util.DateTimeFormatter;
import com.vantruong.rating.entity.Rating;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RatingDto(
        String id,
        String content,
        int star,
        Long productId,
        String productName,
        String createdBy,
        String lastName,
        String firstName,
        String createdDate
) {
  public static RatingDto fromEntity(Rating rating) {
    DateTimeFormatter dateTimeFormatter = new DateTimeFormatter();
    return new RatingDto(
            rating.getId(),
            rating.getContent(),
            rating.getRatingStar(),
            rating.getProductId(),
            rating.getProductName(),
            rating.getCreatedBy(),
            rating.getLastName(),
            rating.getFirstName(),
            dateTimeFormatter.format(rating.getCreatedDate())
    );
  }
}
