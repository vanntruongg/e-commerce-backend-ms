package vantruong.cartservice.repository.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vantruong.cartservice.common.CommonResponse;
import vantruong.cartservice.entity.Product;

import java.util.List;

import static vantruong.cartservice.constant.InternalApiEndpoint.*;

@FeignClient(name = "product-service", url = PRODUCT_SERVICE_URL)
public interface ProductClient {

  @PostMapping(value = PRODUCT_GET_BY_IDS, produces = MediaType.APPLICATION_JSON_VALUE)
  CommonResponse<List<Product>> getProductByIds(List<Integer> ids);

  @GetMapping(PRODUCT_GET_STOCK_BY_ID)
  CommonResponse<Integer> getStockProductById(@PathVariable("id") int id);
}
