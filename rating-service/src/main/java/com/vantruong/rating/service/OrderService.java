package com.vantruong.rating.service;

import com.vantruong.common.dto.order.OrderExistsByProductAndUser;
import com.vantruong.rating.client.OrderClient;
import com.vantruong.rating.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderClient orderClient;

  public OrderExistsByProductAndUser checkOrderExistsByProductAndUserWithStatus(String email, Long productId) {
    CommonResponse<OrderExistsByProductAndUser> response = orderClient.checkOrderExistsByProductAndUserWithStatus(email, productId);
    return response.getData();
  }
}
