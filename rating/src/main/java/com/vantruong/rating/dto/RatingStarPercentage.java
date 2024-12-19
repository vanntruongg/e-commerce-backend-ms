package com.vantruong.rating.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingStarPercentage {
  private Integer star;
  private Double percentage;
  private Integer totalRatings;
}
