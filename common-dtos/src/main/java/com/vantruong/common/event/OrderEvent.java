package com.vantruong.common.event;

import com.vantruong.common.entity.OrderDetail;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
  private Integer orderId;

  private String email;

  private String notes;

  private Double totalPrice;

  private OrderEventStatus orderStatus;

  private PaymentStatus paymentStatus;


  private Integer addressId;

  private List<OrderDetail> orderDetails;
}
