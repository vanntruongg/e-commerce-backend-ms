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

//  @Query(value = "WITH RECURSIVE category_tree AS ( " +
//          "    SELECT cat_id, parent_cat_id, cat_name " +
//          "    FROM categories " +
//          "    WHERE cat_id = :categoryId " +
//          "    UNION ALL " +
//          "    SELECT c.cat_id, c.parent_cat_id, c.cat_name " +
//          "    FROM categories c " +
//          "    JOIN category_tree ct ON c.parent_cat_id = ct.cat_id " +
//          ") " +
//          "SELECT p.* " +
//          "FROM products p " +
//          "JOIN category_tree ct ON p.cat_id = ct.cat_id", nativeQuery = true)
//  List<Product> findAllByCategoryAndSubcategories(int categoryId);

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
