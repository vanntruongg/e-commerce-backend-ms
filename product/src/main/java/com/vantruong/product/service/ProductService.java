package com.vantruong.product.service;

import com.vantruong.product.constant.MessageConstant;
import com.vantruong.product.converter.ProductConverter;
import com.vantruong.product.dto.ProductListResponse;
import com.vantruong.product.dto.ProductPost;
import com.vantruong.product.dto.ProductPut;
import com.vantruong.product.dto.ProductResponse;
import com.vantruong.product.entity.Category;
import com.vantruong.product.entity.Product;
import com.vantruong.product.exception.ErrorCode;
import com.vantruong.product.exception.NotFoundException;
import com.vantruong.product.exception.ProductCreationException;
import com.vantruong.product.repository.ProductRepository;
import com.vantruong.product.viewmodel.ProductInventoryVm;
import com.vantruong.product.viewmodel.SizeQuantityVm;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
  ProductRepository productRepository;
  CategoryService categoryService;
  ProductConverter productConverter;
  InventoryService inventoryService;
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

  public ProductListResponse getListProductByCustomer(Long categoryId, String sortOrder, int pageNo, int pageSize) {
    Pageable pageable = createPaging(pageNo, pageSize, sortOrder);
//    List<com.vantruong.common.dto.response.ProductResponse> productResponses;
    Page<Product> productPage;

//    nếu không truyền category
    if (categoryId != 0) {
      List<Long> categoryIds = categoryService.getAllSubCategoryIds(categoryId);
      if (categoryIds.isEmpty()) {
        productPage = productRepository.findAllByCategoryId(categoryId, pageable);
      } else {
        productPage = productRepository.findAllByCategoryIds(categoryIds, pageable);
      }
    } else {
      productPage = productRepository.findAll(pageable);
    }
    List<ProductResponse> productListResponse = productPage.getContent().stream()
            .map(productConverter::convertToProductResponse)
            .toList();

    return buildProductListResponse(productPage, productListResponse);
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
  public ProductListResponse getListProduct(int pageNo, int pageSize) {
    Sort sort = Sort.by(Sort.Order.asc("id"));
    Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

    Page<Product> productPage = productRepository.findAll(pageable);
    ProductInventoryVm productInventoryVm = inventoryService.getInventoryByProductIds(productPage.getContent());
    List<ProductResponse> productListResponse = productPage.getContent().stream()
            .map(product -> {
              List<SizeQuantityVm> sizeQuantityDtoList = productInventoryVm.productInventoryVm().get(product.getId());
              return productConverter.convertToProductResponse(product, sizeQuantityDtoList);
            })
            .toList();

    return buildProductListResponse(productPage, productListResponse);
  }

  private ProductListResponse buildProductListResponse(Page<Product> productPage, List<ProductResponse> productListResponse) {
    return new ProductListResponse(
            productListResponse,
            productPage.getNumber(),
            productPage.getSize(),
            (int) productPage.getTotalElements(),
            productPage.getTotalPages(),
            productPage.isLast()
    );
  }

  public ProductListResponse searchByName(String productName, int pageNo, int pageSize) {
    Pageable pageable = PageRequest.of(pageNo, pageSize);

    Page<Product> productPage = productRepository.findProductByNameContainingIgnoreCase(productName, pageable);

    ProductInventoryVm productInventoryResponse = inventoryService.getInventoryByProductIds(productPage.getContent());
    List<ProductResponse> productListResponse = productPage.getContent().stream()
            .map(product -> {
              List<SizeQuantityVm> sizeQuantityDtoList = productInventoryResponse.productInventoryVm().get(product.getId());
              return productConverter.convertToProductResponse(product, sizeQuantityDtoList);
            })
            .toList();

    return buildProductListResponse(productPage, productListResponse);
  }

  public Product getProductById(Long id) {
    return productRepository.findById(id).orElseThrow(() ->
            new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));
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


  @Transactional
  public Boolean createProduct(ProductPost productPost) {
    try {
      Category category = categoryService.getCategoryById(productPost.categoryId());

      Product product = Product.builder()
              .name(productPost.name())
              .price(productPost.price())
              .material(productPost.material())
              .style(productPost.style())
              .imageUrl(productPost.imageUrl())
              .description(productPost.description())
              .category(category)
              .build();
      Product savedProduct = productRepository.save(product);

     Boolean createInventorySuccess = inventoryService.createInventory(savedProduct.getId(), productPost.stock());
     if(!createInventorySuccess) {
       throw new ProductCreationException(ErrorCode.PRODUCT_CREATION_FAILED, MessageConstant.PRODUCT_CREATION_FAILED);
     }

//    List<ProductImage> savedProductImages = productImageService.createProductImage(product, productPost.imageUrls());
//    product.setImages(savedProductImages);

//      return productConverter.convertToProductResponse(product);
      return true;
    } catch (Exception e) {
      throw new ProductCreationException(ErrorCode.PRODUCT_CREATION_FAILED,MessageConstant.PRODUCT_CREATION_FAILED);
    }
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
    product.setImageUrl(productPut.imageUrl());
    product.setDescription(productPut.description());
    product.setCategory(category);
    productRepository.save(product);
    return true;
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
