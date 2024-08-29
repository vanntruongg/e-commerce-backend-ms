package com.vantruong.cart.repository.client;

import com.vantruong.cart.common.CommonResponse;
import com.vantruong.common.dto.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.vantruong.cart.constant.InternalApiEndpoint.PRODUCT_GET_BY_IDS;
import static com.vantruong.cart.constant.InternalApiEndpoint.PRODUCT_SERVICE_URL;

@FeignClient(name = "product-service", url = PRODUCT_SERVICE_URL)
public interface ProductClient {

  @PostMapping(value = PRODUCT_GET_BY_IDS, produces = MediaType.APPLICATION_JSON_VALUE)
  CommonResponse<List<ProductResponse>> getProductByIds(List<Integer> ids);

//  @GetMapping(PRODUCT_GET_STOCK_BY_ID)
//  CommonResponse<Integer> getStockProductById(@PathVariable("id") int id);
}
