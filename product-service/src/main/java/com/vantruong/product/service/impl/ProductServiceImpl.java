package com.vantruong.product.service.impl;

import com.vantruong.common.dto.SizeQuantityDto;
import com.vantruong.common.dto.request.ProductInventoryRequest;
import com.vantruong.common.dto.response.ProductInventoryResponse;
import com.vantruong.common.dto.response.ProductResponse;
import com.vantruong.common.exception.ErrorCode;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.product.common.CommonResponse;
import com.vantruong.product.constant.MessageConstant;
import com.vantruong.product.converter.CategoryConverter;
import com.vantruong.product.converter.ProductConverter;
import com.vantruong.product.dto.ProductDto;
import com.vantruong.product.entity.Category;
import com.vantruong.product.entity.Product;
import com.vantruong.product.entity.ProductImage;
import com.vantruong.product.repository.ProductRepository;
import com.vantruong.product.repository.client.InventoryClient;
import com.vantruong.product.service.CategoryService;
import com.vantruong.product.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
  ProductRepository productRepository;
  CategoryService categoryService;
  ProductConverter productConverter;
  InventoryClient inventoryClient;
  //  private final ProductSpecification specification;
  static int PAGE_SIZE = 8;

  private Pageable createPaging(int pageNo, int pageSize) {
    pageNo = Math.max(0, pageNo - 1);
    if (pageSize == 0)
      pageSize = PAGE_SIZE;

    return PageRequest.of(pageNo, pageSize);
  }

  @Override
  public Page<ProductResponse> getAllProduct(int categoryId, String order, int pageNo, int pageSize) {
    Pageable pageable = createPaging(pageNo, pageSize);
    List<ProductResponse> productResponses;

//    nếu không truyền category
    if (categoryId != 0) {
      productResponses = getAllProductByCategoryId(categoryId);

      // sort list product before using paging because PageImpl not sort
      List<ProductResponse> sortedList = sortList(productResponses, order);

//      tính toán phạm vi phân trang
      int first = Math.min(Long.valueOf(pageable.getOffset()).intValue(), sortedList.size());
      int last = Math.min(first + pageable.getPageSize(), sortedList.size());

      return new PageImpl<>(sortedList.subList(first, last), pageable, sortedList.size());
    } else {
      List<ProductResponse> allProducts = productRepository.findAll(createPaging(pageNo, pageSize))
              .stream()
              .map(productConverter::convertToProductResponse)
              .toList();
      List<ProductResponse> sortedList = sortList(allProducts, order);
      return new PageImpl<>(sortedList, pageable, productRepository.count());
    }
  }

  @Override
  public ProductResponse getProductWithCategoryById(int id) {
    Product product = getProductById(id);
    List<Category> categories = categoryService.getAllLevelParentByCategory(product.getCategory().getId());
    List<SizeQuantityDto> sizeQuantityDtos = inventoryClient.getInventoryByProductId(product.getId()).getData();
    return productConverter.convertToProductResponseAndCategories(product, categories, sizeQuantityDtos);
  }


  private Product getProductById(int id) {
    return productRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));
  }

  /**
   * recursion func
   *
   * @param id
   * @return
   */
  @Override
  public List<ProductResponse> getAllProductByCategoryId(int id) {
    List<Product> products = productRepository.findAllByCategoryAndSubcategories(id);
    return productConverter.convertToListProductResponse(products);
  }

  /**
   * @param products
   * @param order:   asc || desc
   * @return List
   */
  private List<ProductResponse> sortList(List<ProductResponse> products, String order) {
    // if order empty return list product not sort
    if (order.isEmpty()) {
      return products;
    }
    Comparator<ProductResponse> comparator = Comparator.comparing(ProductResponse::getPrice);
    if ("desc".equalsIgnoreCase(order)) {
      comparator = comparator.reversed();
    }
    return products.stream()
            .sorted(comparator)
            .collect(Collectors.toList());
  }

  @Override
  public ProductResponse createProduct(ProductDto productDto) {
    Category category = categoryService.getCategoryById(productDto.getCategoryId());
    Product product = new Product();
    convertProductDtoToProduct(product, productDto);
    product.setCategory(category);
    Product productSaved = productRepository.save(product);
    return productConverter.convertToProductResponse(productSaved);
  }

  private void convertProductDtoToProduct(Product product, ProductDto productDto) {
    List<ProductImage> productImages = new ArrayList<>();
    for (String imageUrl : productDto.getImageUrl()) {
      ProductImage productImageBuilder = ProductImage.builder()
              .product(product)
              .imageUrl(imageUrl)
              .build();
      productImages.add(productImageBuilder);
    }

    product.setName(productDto.getName());
    product.setPrice(productDto.getPrice());
    product.setMaterial(productDto.getMaterial());
    product.setStyle(productDto.getStyle());
    product.setImages(productImages);
  }

  /**
   * @param name
   * @param limit
   * @return list product with name like %name% and ignoreCase
   */
  @Override
  public List<ProductResponse> findProductByName(String name, int limit) {
    return productConverter.convertToListProductResponse(
            productRepository.findProductByNameContainingIgnoreCase(name.trim(), Limit.of(limit))
    );
  }

  @Override
  public Boolean updateProduct(ProductDto productDto) {
    Product product = getProductById(productDto.getId());
    Category category = categoryService.getCategoryById(productDto.getCategoryId());
    convertProductDtoToProduct(product, productDto);
    product.setCategory(category);
    productRepository.save(product);
    return true;
  }

  @Override
  public List<ProductResponse> getAll() {
    Sort sort = Sort.by(Sort.Order.asc("id"));
    List<Product> products = productRepository.findAll(sort);
    List<Integer> productIds = products.stream().map(Product::getId).toList();
    CommonResponse<ProductInventoryResponse> inventoryResponse = inventoryClient.getAllInventoryByProductIds(ProductInventoryRequest.builder().productIds(productIds).build());
    return productConverter.convertToListProductResponse(products, inventoryResponse.getData().getProductInventoryResponse());
  }

  @Override
  public Long getProductCount() {
    return productRepository.getProductCount();
  }

  @Override
  public List<ProductResponse> getProductsByCategoryId(int id, int limit) {
    return productConverter.convertToListProductResponse(productRepository.findAllByCategoryId(id, Limit.of(limit)));
  }

  @Override
  public List<ProductResponse> getProductsByIds(List<Integer> productIds) {
    List<Product> products = productRepository.findAllById(productIds);
    return productConverter.convertToListProductResponse(products);
  }

}
