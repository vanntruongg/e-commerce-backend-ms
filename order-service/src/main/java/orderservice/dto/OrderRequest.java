package orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
  private int orderId;
  private String email;
  private Integer addressId;
  private float totalPrice;
  private String notes;
  private int paymentMethodId;
  private List<OrderDetailDto> listProduct;
}
