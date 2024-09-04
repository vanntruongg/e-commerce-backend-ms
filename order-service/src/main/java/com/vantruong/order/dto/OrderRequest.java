package com.vantruong.order.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
//  @NotNull
//  private Integer orderId;
  @NotNull
  private String email;
  @NotNull
  private String name;
  @NotNull
  private String phone;
  @NotNull
  private String address;
  //  @NotNull
//  private Double totalPrice;
  private String notes;
  @NotNull
  private Integer paymentMethodId;
  @NotEmpty
  private List<OrderDetailRequest> listProduct;
}
