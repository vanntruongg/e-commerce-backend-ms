package vantruong.cartservice.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product  {
  private int id;

  private String name;

  private double price;

  private String material;

  private String style;

  private String imageUrl;
  private Category category;
  private int stock;
}
