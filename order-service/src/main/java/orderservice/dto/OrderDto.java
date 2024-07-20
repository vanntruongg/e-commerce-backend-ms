package orderservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import orderservice.entity.OrderDetail;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderDto {
  private int orderId;
  private String email;
  private int addressId;
  private String notes;
  private double totalPrice;
  private String orderStatus;
  private String paymentMethod;
  private LocalDateTime createdDate;
  private List<OrderDetail> orderDetail;
}
