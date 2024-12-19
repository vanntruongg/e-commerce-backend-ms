package com.vantruong.rating.client;

import com.vantruong.common.dto.order.OrderExistsByProductAndUser;
import com.vantruong.rating.common.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.vantruong.common.constant.InternalApiEndpoint.CHECK_ORDER_BY_USER_PRODUCT_STATUS;
import static com.vantruong.common.constant.InternalApiEndpoint.ORDER_SERVICE_URL;

@FeignClient(name = "order-service", url = ORDER_SERVICE_URL)
public interface OrderClient {

  @GetMapping(CHECK_ORDER_BY_USER_PRODUCT_STATUS)
  CommonResponse<OrderExistsByProductAndUser> checkOrderExistsByProductAndUserWithStatus(
          @PathVariable("email") String email,
          @PathVariable("id") Long productId
  );
}
