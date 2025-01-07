package com.vantruong.cart.client;

import com.vantruong.cart.common.CommonResponse;
import com.vantruong.cart.viewmodel.ProductVm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.vantruong.cart.constant.InternalApiEndpoint.PRODUCT_GET_BY_IDS;
import static com.vantruong.cart.constant.InternalApiEndpoint.PRODUCT_SERVICE_URL;

@FeignClient(name = "product-service", url = PRODUCT_SERVICE_URL)
public interface ProductClient {
  @PostMapping(value = PRODUCT_GET_BY_IDS, produces = MediaType.APPLICATION_JSON_VALUE)
  CommonResponse<List<ProductVm>> getProductByIds(List<Long> ids);
}
