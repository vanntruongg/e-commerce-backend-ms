package com.vantruong.product.converter;

import com.vantruong.common.dto.SizeQuantityDto;
import com.vantruong.common.dto.response.ProductImageResponse;
import com.vantruong.common.dto.response.ProductResponse;
import com.vantruong.product.entity.Category;
import com.vantruong.product.entity.Product;
import com.vantruong.product.entity.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProductConverter {
  private final CategoryConverter categoryConverter;

  public ProductResponse convertToProductResponseAndCategories(Product product, List<Category> categories, List<SizeQuantityDto> sizeQuantityDtos) {
    ProductResponse productResponse = convertToProductResponse(product);
    productResponse.setCategories(categoryConverter.convertToListCategoryResponse(categories));
    productResponse.setSizeQuantity(sizeQuantityDtos);
    return productResponse;
  }

  public ProductResponse convertToProductResponse(Product product) {
    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .material(product.getMaterial())
            .style(product.getStyle())
            .category(categoryConverter.convertToCategoryResponse(product.getCategory()))
            .images(convertToProductImageResponse(product.getImages()))
            .build();
  }
  public ProductResponse convertToProductResponse(Product product, List<SizeQuantityDto> sizeQuantityDtos) {
    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .material(product.getMaterial())
            .style(product.getStyle())
            .category(categoryConverter.convertToCategoryResponse(product.getCategory()))
            .images(convertToProductImageResponse(product.getImages()))
            .sizeQuantity(sizeQuantityDtos)
            .build();
  }

  public List<ProductResponse> convertToListProductResponse(List<Product> products) {
    return products.stream().map(this::convertToProductResponse).toList();
  }

  public List<ProductResponse> convertToListProductResponse(List<Product> products, Map<Integer, List<SizeQuantityDto>> inventories) {
    List<ProductResponse> productResponses = products.stream().map(this::convertToProductResponse).toList();

    productResponses.forEach(productResponse -> {
              List<SizeQuantityDto> sizeQuantityDto = inventories.get(productResponse.getId());
              productResponse.setSizeQuantity(sizeQuantityDto);
            });
    return productResponses;
  }


  private ProductImageResponse convertToProductImage(ProductImage productImage) {
    return ProductImageResponse.builder()
            .id(productImage.getId())
            .imageUrl(productImage.getImageUrl())
            .build();
  }

  public List<ProductImageResponse> convertToProductImageResponse(List<ProductImage> productImages) {
    return productImages.stream().map(this::convertToProductImage).toList();
  }
}
