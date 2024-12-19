package com.vantruong.cart.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("Cart")
public class Cart {
  @Id
  private String email;
  private List<CartItem> items;
}
