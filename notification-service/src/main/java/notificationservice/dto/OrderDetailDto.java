package notificationservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderDetailDto {
  private int orderDetailId;

  private int productId;

  private int quantity;

  private String productName;

  private float productPrice;

  private String productImage;
}
