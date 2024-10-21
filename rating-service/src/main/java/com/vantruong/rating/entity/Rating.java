package com.vantruong.rating.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "rating")
public class Rating extends AbstractAuditEntity {
  @Id
  private String id;
  private String content;
  private Integer ratingStar;
  @Indexed
  private Long productId;
  private String lastName;
  private String firstName;
  @Builder.Default
  private Set<String> upvoteUsers = new HashSet<>();
}
