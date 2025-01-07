package com.vantruong.product.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.vantruong.product.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Query("select p from Product p where p.category.id = :id")
  List<Product> findAllByCategoryId(int id, Limit limit);

  Page<Product> findAllByCategoryId(Long categoryId, Pageable page);
  @Query("select p from Product p where p.category.id in :categoryIds")
  Page<Product> findAllByCategoryIds(List<Long> categoryIds, Pageable page);

  List<Product> findProductByNameContainingIgnoreCase(String name, Limit limit);
  Page<Product> findProductByNameContainingIgnoreCase(String name, Pageable pageable);

  @Query("select count(p) from Product p")
  Long getProductCount();

  @Query("select p.price from Product p where p.id = :productId")
  double findPriceById(Long productId);

}
