package com.vantruong.inventory.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "inventory")
public class Inventory {
  @Id
  private String id;
  private Long productId;
  private List<SizeQuantity> sizes;
}
