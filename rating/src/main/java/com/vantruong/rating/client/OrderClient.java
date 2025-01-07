package com.vantruong.rating.client;

import com.vantruong.rating.common.CommonResponse;
import com.vantruong.rating.viewmodel.OrderCheckResultVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.vantruong.rating.constant.InternalApiEndpoint.*;

@FeignClient(name = "order-service", url = ORDER_SERVICE_URL + INTERNAL)
public interface OrderClient {

  @GetMapping(CHECK_ORDER_BY_USER_PRODUCT_STATUS)
  CommonResponse<OrderCheckResultVm> checkOrderExistsByProductAndUserWithStatus(
          @PathVariable("email") String email,
          @PathVariable("id") Long productId
  );
}
