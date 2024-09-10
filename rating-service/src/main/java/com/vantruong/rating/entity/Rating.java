package com.vantruong.rating.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
  private int ratingStar;
  private Long productId;
  private String productName;
  private String lastName;
  private String firstName;
}
