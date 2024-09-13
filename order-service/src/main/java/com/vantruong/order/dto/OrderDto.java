package com.vantruong.order.dto;

import com.vantruong.order.entity.enumeration.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
public class OrderDto {
  private Long orderId;
  private String email;
  private String name;
  private String phone;
  private String address;
  private String notes;
  private double totalPrice;
  private String orderStatus;
  private String paymentStatus;
  private PaymentMethod paymentMethod;
  private String created;
  private LocalDateTime createdDate;
  private Set<OrderItemDto> orderItems;
}
