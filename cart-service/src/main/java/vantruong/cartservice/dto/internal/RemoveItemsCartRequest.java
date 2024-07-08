package vantruong.cartservice.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RemoveItemsCartRequest {
  private String email;
  private List<Integer> productIds;
}
