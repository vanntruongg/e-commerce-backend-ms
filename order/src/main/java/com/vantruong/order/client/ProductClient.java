package com.vantruong.order.client;

import com.vantruong.order.common.CommonResponse;
import com.vantruong.order.viewmodel.CalculateTotalOrderPricePostVm;
import com.vantruong.order.viewmodel.CalculateTotalOrderPriceVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.vantruong.order.constant.InternalApiEndpoint.*;


@FeignClient(name = "product-service", url = PRODUCT_SERVICE_URL + INTERNAL)
public interface ProductClient {

  //  map <productId, quantity>
  @PostMapping(value = CALCULATE_BY_PRODUCT_IDS, produces = MediaType.APPLICATION_JSON_VALUE)
  CommonResponse<CalculateTotalOrderPriceVm> calculateTotalOrderPrice(@RequestBody CalculateTotalOrderPricePostVm request);

}
