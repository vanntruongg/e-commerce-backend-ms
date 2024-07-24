package com.vantruong.product.service;

import org.springframework.data.domain.Page;
import com.vantruong.product.entity.dto.ProductDto;
import com.vantruong.product.entity.Product;
import com.vantruong.product.entity.dto.ProductResponse;

import java.util.List;
import java.util.Map;

public interface ProductService {
  Page<Product> getAllProduct(int categoryId, String order, int pageNo, int pageSize);

  ProductResponse getProductWithCategoryById(int id);
  Product getProductById(int id);

  List<Product> getAllProductByCategoryId(int id);

  List<Product> sortList(List<Product> products, String order);

  Product createProduct(ProductDto productDto);

  List<Product> findProductByName(String name, int limit);

  int getStockById(int id);

  Boolean updateProductQuantityByOrder(Map<Integer, Integer> stockUpdate);

  Boolean updateProduct(ProductDto productDto);

  List<Product> getAll();

  Long getProductCount();

  List<Product> getProductsByCategoryId(int id, int limit);

  List<Product> getProductsByIds(List<Integer> productIds);
}
