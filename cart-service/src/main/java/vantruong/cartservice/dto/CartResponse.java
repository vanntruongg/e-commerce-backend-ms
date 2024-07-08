package vantruong.cartservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import vantruong.cartservice.entity.Item;

import java.util.List;

@Getter
@Setter
@Builder
public class CartResponse {
  private String email;
  private List<ItemResponse> items;
  private double totalPrice;
}
