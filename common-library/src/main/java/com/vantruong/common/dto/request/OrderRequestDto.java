package com.vantruong.common.dto.request;

import com.vantruong.common.event.OrderEventStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequestDto {
  Integer userId;
  Integer productId;
  Integer orderId;
  Double amount;
  OrderEventStatus orderStatus;
}
