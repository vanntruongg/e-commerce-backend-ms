package com.vantruong.order.client;

import com.vantruong.common.dto.response.ProductResponse;
import com.vantruong.order.common.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static com.vantruong.common.constant.InternalApiEndpoint.*;

@FeignClient(name = "product-service", url = PRODUCT_SERVICE_URL)
public interface ProductClient {

  @PostMapping(value = GET_BY_PRODUCT_ID, produces = MediaType.APPLICATION_JSON_VALUE)
  CommonResponse<ProductResponse> getProductById(@PathVariable("id") Integer id);


//  map <productId, quantity>
  @PostMapping(value = CALCULATE_BY_PRODUCT_IDS, produces = MediaType.APPLICATION_JSON_VALUE)
  CommonResponse<Double> calculateTotalOrderPrice(@RequestBody Map<Long, Integer> productQuantities);

}
