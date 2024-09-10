package com.vantruong.rating.service;

import com.vantruong.common.dto.user.UserCommonDto;
import com.vantruong.common.exception.AccessDeniedException;
import com.vantruong.common.exception.Constant;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.common.exception.ResourceExistedException;
import com.vantruong.rating.constant.MessageConstant;
import com.vantruong.rating.dto.RatingDto;
import com.vantruong.rating.dto.RatingListDto;
import com.vantruong.rating.dto.RatingPost;
import com.vantruong.rating.entity.Rating;
import com.vantruong.rating.repository.RatingRepository;
import com.vantruong.rating.util.AuthenticationUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingService {
  RatingRepository ratingRepository;
  OrderService orderService;
  UserService userService;

  public RatingListDto getRatingListByProductId(Long productId, int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("createdDate").descending());

    Page<Rating> ratingPage = ratingRepository.findByProductId(productId, pageable);

    List<RatingDto> ratingDtoList = new ArrayList<>();
    for (Rating rating : ratingPage.getContent()) {
      ratingDtoList.add(RatingDto.fromEntity(rating));
    }

    return new RatingListDto(ratingDtoList, ratingPage.getTotalElements(), ratingPage.getTotalPages());
  }

  public RatingDto createRating(RatingPost ratingPost) {
    String userId = AuthenticationUtils.extractUserId();

    if (!orderService.checkOrderExistsByProductAndUserWithStatus(
            ratingPost.productId()
    ).isPresent()) {
      throw new AccessDeniedException(Constant.ErrorCode.DENIED, Constant.Message.ACCESS_DENIED);
    }

    if (ratingRepository.existsByCreatedByAndProductId(userId, ratingPost.productId())) {
      throw new ResourceExistedException(Constant.ErrorCode.RESOURCE_ALREADY_EXISTED, Constant.Message.RESOURCE_ALREADY_EXISTED);
    }

    UserCommonDto userCommonDto = userService.getUser();

    Rating rating = Rating.builder()
            .content(ratingPost.content())
            .ratingStar(ratingPost.star())
            .productId(ratingPost.productId())
            .productName(ratingPost.productName())
            .lastName(userCommonDto.lastName())
            .firstName(userCommonDto.firstName())
            .build();
    Rating savedRating = ratingRepository.save(rating);

    return RatingDto.fromEntity(savedRating);
  }

  public Boolean deleteRating(String ratingId) {
    Rating rating = ratingRepository.findById(ratingId)
            .orElseThrow(() -> new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.RATING_NOT_FOUND));

    ratingRepository.delete(rating);
    return true;
  }

  public Double calculateAverageStar(Long productId) {
    List<Object[]> totalStarsAndRatings = ratingRepository.getTotalStarsAndTotalRatings(productId);
    if (ObjectUtils.isEmpty(totalStarsAndRatings.get(0)[0])) {
      return 0.0;
    }
    int totalStars = Integer.parseInt(totalStarsAndRatings.get(0)[0].toString());
    int totalRatings = Integer.parseInt(totalStarsAndRatings.get(0)[1].toString());
    return (totalStars * 1.0) / totalRatings;
  }

}
