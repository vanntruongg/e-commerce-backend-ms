package com.vantruong.common.dto.request;

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
  private Integer addressId;
  @NotNull
  private Double totalPrice;
  private String notes;
  @NotNull
  private Integer paymentMethodId;
  @NotEmpty
  private List<OrderDetailRequest> listProduct;
}
