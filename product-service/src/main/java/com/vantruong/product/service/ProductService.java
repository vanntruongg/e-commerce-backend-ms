package com.vantruong.product.service;

import com.vantruong.common.exception.Constant;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.product.constant.MessageConstant;
import com.vantruong.product.converter.ProductConverter;
import com.vantruong.product.dto.ProductListResponse;
import com.vantruong.product.dto.ProductPost;
import com.vantruong.product.dto.ProductPut;
import com.vantruong.product.dto.ProductResponse;
import com.vantruong.product.entity.Category;
import com.vantruong.product.entity.Product;
import com.vantruong.product.entity.ProductImage;
import com.vantruong.product.repository.ProductImageRepository;
import com.vantruong.product.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
  ProductRepository productRepository;
  ProductImageRepository productImageRepository;
  CategoryService categoryService;
  ProductConverter productConverter;

  static Integer PAGE_SIZE = 8;

  private Pageable createPaging(int pageNo, int pageSize, String sortOrder) {
    pageNo = Math.max(0, pageNo - 1);
    if (pageSize == 0) pageSize = PAGE_SIZE;

    if (sortOrder == null || sortOrder.isEmpty()) {
      return PageRequest.of(pageNo, pageSize);
    }

    Sort sort = Sort.by("price");
    if (sortOrder.equalsIgnoreCase("desc")) {
      sort = sort.descending();
    } else if (sortOrder.equalsIgnoreCase("asc")) {
      sort = sort.ascending();
    }
    return PageRequest.of(pageNo, pageSize, sort);
  }

  public ProductListResponse getAllProduct(Long categoryId, String sortOrder, int pageNo, int pageSize) {
    Pageable pageable = createPaging(pageNo, pageSize, sortOrder);
//    List<com.vantruong.common.dto.response.ProductResponse> productResponses;
    Page<Product> productPage;

//    nếu không truyền category
    if (categoryId != 0) {
      List<Long> categoryIds = categoryService.getAllSubCategoryIds(categoryId);
      if(categoryIds.isEmpty()) {
        productPage = productRepository.findAllByCategoryId(categoryId, pageable);
      } else {
        productPage = productRepository.findAllByCategoryIds(categoryIds, pageable);
      }
    } else {
      productPage = productRepository.findAll(pageable);
    }
    List<Product> productList = productPage.getContent();
    List<ProductResponse> productListResponse = productList.stream()
            .map(productConverter::convertToProductResponse)
            .toList();

    return new ProductListResponse(
            productListResponse,
            productPage.getNumber(),
            productPage.getSize(),
            (int) productPage.getTotalElements(),
            productPage.getTotalPages(),
            productPage.isLast()
    );
  }

  public Product getProductById(Long id) {
    return productRepository.findById(id).orElseThrow(() ->
            new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));
  }

  public Double calculateTotalOrderPrice(Map<Long, Integer> productQuantities) {

    double totalPrice = 0.0;
    for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
      Long productId = entry.getKey();
      Integer quantity = entry.getValue();

      double price = productRepository.findPriceById(productId);

      totalPrice += price * quantity;

    }
    return totalPrice;
  }


  public ProductResponse createProduct(ProductPost productPost) {
    Category category = categoryService.getCategoryById(productPost.id());

    Product product = Product.builder()
            .name(productPost.name())
            .price(productPost.price())
            .material(productPost.material())
            .style(productPost.style())
            .category(category)
            .build();
    productRepository.save(product);

    List<ProductImage> productImages = productPost.imageUrl().stream()
            .map(imageUrl -> ProductImage.builder()
                    .imageUrl(imageUrl)
                    .product(product)
                    .build())
            .toList();
    List<ProductImage> savedProductImages = productImageRepository.saveAll(productImages);
    product.setImages(savedProductImages);

    return productConverter.convertToProductResponse(product);
  }

  public List<ProductResponse> findProductByName(String name, int limit) {
    return productConverter.convertToListProductResponse(
            productRepository.findProductByNameContainingIgnoreCase(name.trim(), Limit.of(limit)));
  }

  public Boolean updateProduct(ProductPut productPut) {
    Product product = getProductById(productPut.id());
    Category category = categoryService.getCategoryById(productPut.categoryId());
    product.setName(productPut.name());
    product.setPrice(productPut.price());
    product.setMaterial(productPut.material());
    product.setStyle(productPut.style());
    product.setCategory(category);
    productRepository.save(product);
    return true;
  }

  public List<ProductResponse> getAll() {
    Sort sort = Sort.by(Sort.Order.asc("id"));
    List<Product> products = productRepository.findAll(sort);
    return productConverter.convertToListProductResponse(products);
  }

  public Long getProductCount() {
    return productRepository.getProductCount();
  }


  public List<ProductResponse> getProductsByCategoryId(int id, int limit) {
    return productConverter.convertToListProductResponse(productRepository.findAllByCategoryId(id, Limit.of(limit)));
  }

  public List<ProductResponse> getProductsByIds(List<Long> productIds) {
    List<Product> products = productRepository.findAllById(productIds);
    return productConverter.convertToListProductResponse(products);
  }

}
