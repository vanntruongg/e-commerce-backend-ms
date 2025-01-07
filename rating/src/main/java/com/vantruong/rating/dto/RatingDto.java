package com.vantruong.rating.dto;

import com.vantruong.rating.entity.Rating;
import com.vantruong.rating.util.DateTimeFormatter;
import lombok.Builder;

import java.util.Set;

@Builder
public record RatingDto(
        String id,
        String content,
        int star,
        Long productId,
        Set<String> upvoteUsers,
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
            rating.getUpvoteUsers(),
            rating.getCreatedBy(),
            rating.getLastName(),
            rating.getFirstName(),
            dateTimeFormatter.format(rating.getCreatedDate())
    );
  }
}
