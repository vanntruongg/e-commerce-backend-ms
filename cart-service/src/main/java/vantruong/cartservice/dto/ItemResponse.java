package vantruong.cartservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vantruong.cartservice.entity.Product;

@Getter
@Setter
@Builder
public class ItemResponse {
  private Product product;
  private int quantity;
}
