package com.vantruong.product.service;

import com.vantruong.common.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import com.vantruong.product.dto.ProductDto;
import com.vantruong.product.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
  Page<ProductResponse> getAllProduct(int categoryId, String order, int pageNo, int pageSize);

  ProductResponse getProductWithCategoryById(int id);

  List<ProductResponse> getAllProductByCategoryId(int id);

  ProductResponse createProduct(ProductDto productDto);

  List<ProductResponse> findProductByName(String name, int limit);

  Boolean updateProduct(ProductDto productDto);

  List<ProductResponse> getAll();

  Long getProductCount();

  List<ProductResponse> getProductsByCategoryId(int id, int limit);

  List<ProductResponse> getProductsByIds(List<Integer> productIds);

 Product getProductById(int id);

  Double calculateTotalPriceByProductIds(Map<Integer, Integer> productQuantities);
}
