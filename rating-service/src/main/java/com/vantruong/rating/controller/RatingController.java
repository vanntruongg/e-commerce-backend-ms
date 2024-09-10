package com.vantruong.rating.controller;

import com.vantruong.rating.common.CommonResponse;
import com.vantruong.rating.constant.MessageConstant;
import com.vantruong.rating.dto.RatingPost;
import com.vantruong.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.rating.constant.ApiEndpoint.*;

@RestController
@RequestMapping(RATINGS)
@RequiredArgsConstructor
public class RatingController {
  private final RatingService ratingService;

  @GetMapping(GET_RATING_LIST)
  public ResponseEntity<CommonResponse<Object>> getRatingList(
          @PathVariable("productId") Long productId,
          @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
          @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(ratingService.getRatingListByProductId(productId, pageNo, pageSize))
            .build());
  }

  @PostMapping
  public ResponseEntity<CommonResponse<Object>> createRating(RatingPost ratingPost) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(ratingService.createRating(ratingPost))
            .build());
  }

  @DeleteMapping(ID_PARAM)
  public ResponseEntity<CommonResponse<Object>> deleteRating(@PathVariable("id") String ratingId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.DELETE_SUCCESS)
            .data(ratingService.deleteRating(ratingId))
            .build());
  }

  @PostMapping(GET_AVERAGE_RATING_OF_PRODUCT)
  public ResponseEntity<CommonResponse<Object>> getAverageStarRatingOfProduct(
          @PathVariable("productId") Long productId
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(ratingService.calculateAverageStar(productId))
            .build());
  }
}
