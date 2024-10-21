package com.vantruong.common.event;

import com.vantruong.common.dto.order.OrderItemCommonDto;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderEvent {
  private Long orderId;
  private String email;
  private String notes;
  private Double totalPrice;
  private String orderStatus;
  private OrderEventStatus orderEventStatus;
  private PaymentStatus paymentStatus;
  private String name;
  private String phone;
  private String address;
  private Set<OrderItemCommonDto> orderItems;
}
