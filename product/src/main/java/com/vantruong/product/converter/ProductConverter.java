package com.vantruong.product.converter;

import com.vantruong.product.dto.ProductResponse;
import com.vantruong.product.entity.Product;
import com.vantruong.product.viewmodel.SizeQuantityVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductConverter {
  private final CategoryConverter categoryConverter;

  public ProductResponse convertToProductResponse(Product product) {
    return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getMaterial(),
            product.getStyle(),
            categoryConverter.convertToCategoryResponse(product.getCategory()),
            product.getImageUrl(),
            product.getDescription(),
            new ArrayList<>()
    );
  }

  public ProductResponse convertToProductResponse(Product product, List<SizeQuantityVm> sizeQuantityDtoList) {
    return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getMaterial(),
            product.getStyle(),
            categoryConverter.convertToCategoryResponse(product.getCategory()),
            product.getImageUrl(),
            product.getDescription(),
            sizeQuantityDtoList
    );
  }

  public List<ProductResponse> convertToListProductResponse(List<Product> products) {
    return products.stream().map(this::convertToProductResponse).toList();
  }

}
