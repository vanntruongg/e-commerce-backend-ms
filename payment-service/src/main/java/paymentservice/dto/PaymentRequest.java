package paymentservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import paymentservice.enums.PaymentMethod;

@Getter
@Setter
@Builder
public class PaymentRequest {
  private Integer orderId;
  private Integer methodId;
  private Double amount;
}
