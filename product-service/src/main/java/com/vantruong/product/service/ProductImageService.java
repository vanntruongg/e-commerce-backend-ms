//package com.vantruong.product.service;
//
//import com.vantruong.product.entity.Product;
//import com.vantruong.product.entity.ProductImage;
//import com.vantruong.product.repository.ProductImageRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class ProductImageService {
//  private final ProductImageRepository productImageRepository;
//
//
//  public List<ProductImage> createProductImage(Product product, List<String> productImageUrls) {
//    List<ProductImage> productImages = productImageUrls.stream()
//            .map(imageUrl -> ProductImage.builder()
//                    .imageUrl(imageUrl)
//                    .product(product)
//                    .build())
//            .toList();
//   return productImageRepository.saveAll(productImages);
//  }
//}
