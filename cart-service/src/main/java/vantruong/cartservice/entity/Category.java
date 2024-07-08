package vantruong.cartservice.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category{
  private int id;
  private String name;
  private String image;
}
