package com.vantruong.rating.repository;

import com.vantruong.rating.entity.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RatingRepositoryCustom {
  private final MongoTemplate mongoTemplate;

  public List<RatingByStar> getRatingGroupByRatingStar(Long productId) {
    Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("productId").is(productId)),
            Aggregation.group("ratingStar").count().as("totalRatings"),
            Aggregation.sort(Sort.by(Sort.Order.asc("ratingStar"))),
            Aggregation.project("totalRatings").and("_id").as("ratingStar")
    );
    AggregationResults<RatingByStar> results = mongoTemplate.aggregate(
            aggregation,
            Rating.class,
            RatingByStar.class
    );
    return results.getMappedResults();
  }
}
