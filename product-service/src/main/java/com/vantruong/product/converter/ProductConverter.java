package com.vantruong.product.converter;

import com.vantruong.common.dto.response.ProductImageResponse;
import com.vantruong.product.dto.ProductResponse;
import com.vantruong.product.entity.Product;
import com.vantruong.product.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
            convertToListProductImageResponse(product.getImages())
    );
  }

  public List<ProductResponse> convertToListProductResponse(List<Product> products) {
    return products.stream().map(this::convertToProductResponse).toList();
  }

  private ProductImageResponse convertToProductImage(ProductImage productImage) {
    return new ProductImageResponse(productImage.getId(), productImage.getImageUrl());
  }

  public List<ProductImageResponse> convertToListProductImageResponse(List<ProductImage> productImages) {
    return productImages.stream().map(this::convertToProductImage).toList();
  }

}
