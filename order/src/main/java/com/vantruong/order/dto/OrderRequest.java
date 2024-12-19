package com.vantruong.order.dto;

import com.vantruong.order.entity.enumeration.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
  @NotNull
  private String name;
  @NotNull
  private String phone;
  @NotNull
  private String address;
  private String notes;
  @NotNull
  private PaymentMethod paymentMethod;
  @NotEmpty
  private List<OrderItemRequest> orderItemRequests;
}
