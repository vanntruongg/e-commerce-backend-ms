package com.vantruong.common.dto.request;

import com.vantruong.common.dto.CartItem;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DeleteCartItemsRequest {
  private String email;
  private List<CartItem> items;
}
