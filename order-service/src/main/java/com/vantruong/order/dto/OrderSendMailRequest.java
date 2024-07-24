package com.vantruong.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.vantruong.order.entity.OrderDetail;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderSendMailRequest {
  private int orderId;
  private String email;
  private String name;
  private String phone;
  private String address;
  private String notes;
  private double totalPrice;
  private String orderStatus;
  private String paymentMethod;
  private LocalDateTime createdDate;
  private List<OrderDetail> orderDetail;
}
