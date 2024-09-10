package com.vantruong.rating.repository;

import com.vantruong.rating.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

  Page<Rating> findByProductId(Long productId, Pageable pageable);

  boolean existsByCreatedByAndProductId(String createdBy, Long productId);

  @Aggregation(pipeline = {
          "{ $match:  { productId: ?0 } }",
          "{ $group:  { _id: null, totalStars:  { $sum:  '$ratingStar' }, totalRatings:  { $sum:  1 } } }"
  })
  List<Object[]> getTotalStarsAndTotalRatings(Long productId);
}
