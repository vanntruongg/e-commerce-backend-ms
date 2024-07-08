package productservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productservice.entity.dto.ProductDto;
import productservice.exception.ErrorCode;
import productservice.exception.NotFoundException;
import productservice.service.CategoryService;
import productservice.constant.MessageConstant;
import productservice.entity.Category;
import productservice.entity.Product;
import productservice.entity.dto.ProductResponse;
import productservice.repository.ProductRepository;
import productservice.service.ProductService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
  ProductRepository productRepository;
  CategoryService categoryService;
  //  private final ProductSpecification specification;
   static int PAGE_SIZE = 8;

  private Pageable createPagingAndSort(String order, int pageNo, int pageSize) {
    pageNo = Math.max(0, pageNo - 1);
    Sort sort = Sort.unsorted();

    if (order != null && (order.equalsIgnoreCase("desc") || order.equalsIgnoreCase("asc"))) {
      sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), "price");
    }

    if(pageSize == 0)
      pageSize = PAGE_SIZE;

    return PageRequest.of(pageNo, pageSize, sort);
  }

  @Override
  public Page<Product> getAllProduct(int categoryId, String order, int pageNo, int pageSize) {
    if (categoryId != 0) {
      List<Product> productList = getAllProductByCategoryId(categoryId);
      Pageable pageable = createPagingAndSort(order, pageNo, pageSize);

      // sort list product before using paging because PageImpl not sort
      List<Product> sortedList = sortList(productList, order);
      int first = Math.min(Long.valueOf(pageable.getOffset()).intValue(), sortedList.size());
      int last = Math.min(first + pageable.getPageSize(), sortedList.size());

      return new PageImpl<>(sortedList.subList(first, last), pageable, sortedList.size());
    }
    return productRepository.findAll(createPagingAndSort(order, pageNo, pageSize));
  }

  @Override
  public ProductResponse getProductWithCategoryById(int id) {
    Product product = getProductById(id);
    List<Category> categories = categoryService.getAllLevelParentByCategory(product.getCategory().getId());
    return ProductResponse.builder()
            .product(product)
            .categories(categories)
            .build();
  }

  @Override
  public Product getProductById(int id) {
    return productRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PRODUCT_NOT_FOUND));
  }

  /**
   * recursion func
   * @param id
   * @return
   */
  @Override
  public List<Product> getAllProductByCategoryId(int id) {
//    List<Category> categories = categoryService.getSubCategoriesByParentId(id);
//
//    if (categories.isEmpty()) {
//      return productRepository.findAllByCategoryId(id);
//    } else {
//      List<Product> productList = new ArrayList<>();
//      for (Category category : categories) {
//        List<Product> products = getAllProductByCategoryId(category.getId());
//        productList.addAll(products);
//      }
//      return productList;
//    }
    return productRepository.findAllByCategoryAndSubcategories(id);
  }

  /**
   * @param products
   * @param order:   asc || desc
   * @return List
   */
  @Override
  public List<Product> sortList(List<Product> products, String order) {
    // if order empty return list product not sort
    if (order.isEmpty()) {
      return products;
    }
    Comparator<Product> comparator = Comparator.comparing(Product::getPrice);
    if (order.equalsIgnoreCase("desc")) {
      comparator = comparator.reversed();
    }
    return products.stream()
            .sorted(comparator)
            .collect(Collectors.toList());
  }

  @Override
  public Product createProduct(ProductDto productDto) {
    Category category = categoryService.getCategoryById(productDto.getCategoryId());
    Product product = new Product();
    convertProductDtoToProduct(product, productDto);
    product.setCategory(category);
    return productRepository.save(product);
  }

  private void convertProductDtoToProduct(Product product, ProductDto productDto) {
    product.setName(productDto.getName());
    product.setPrice(productDto.getPrice());
    product.setMaterial(productDto.getMaterial());
    product.setStyle(productDto.getStyle());
    product.setImageUrl(productDto.getImageUrl());
    product.setStock(productDto.getStock());
  }

  /**
   * @param name
   * @param limit
   * @return list product with name like %name% and ignoreCase
   */
  @Override
  public List<Product> findProductByName(String name, int limit) {
    return productRepository.findProductByNameContainingIgnoreCase(name.trim(), Limit.of(limit));
  }

  @Override
  public int getStockById(int id) {
    Optional<Product> product = productRepository.findById(id);
    return product.map(Product::getStock).orElse(0);
  }

  @Override
  @Transactional
  public Boolean updateProductQuantityByOrder(Map<Integer, Integer> stockUpdate) {
    List<Product> products = new ArrayList<>();
    for (Map.Entry<Integer, Integer> productIdAndQuantity : stockUpdate.entrySet()) {
      int productId = productIdAndQuantity.getKey();
      int quantity = productIdAndQuantity.getValue();

      Product product = getProductById(productId);
      int newQuantity = product.getStock() - quantity;
      if (newQuantity < 0) {
        return false;
      } else {
        product.setStock(newQuantity);
        products.add(product);
      }
    }
    productRepository.saveAll(products);
    return true;
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
  public List<Product> getAll() {
    return productRepository.findAll();
  }

  @Override
  public Long getProductCount() {
    return productRepository.getProductCount();
  }

  @Override
  public List<Product> getProductsByCategoryId(int id, int limit) {
    return productRepository.findAllByCategoryId(id, Limit.of(limit));
  }

  @Override
  public List<Product> getProductsByIds(List<Integer> productIds) {
    return productRepository.findAllById(productIds);
  }
}
