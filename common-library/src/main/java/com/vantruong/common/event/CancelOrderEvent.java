package com.vantruong.common.event;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CancelOrderEvent {
  private Set<CancelOrderItem> orderItems;
}
