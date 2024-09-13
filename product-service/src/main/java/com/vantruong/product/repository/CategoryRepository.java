package com.vantruong.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vantruong.product.entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("select c from Category c where c.parentCategory is null")
  List<Category> findTopLevelCategory();

  @Query("select c from Category c where c.parentCategory.id = :parentId")
  List<Category> findSubcategoriesByParentId(Long parentId);

  @Query("select c.parentCategory from Category c where c.id = :categoryId")
  Optional<Category> findParentCategoryByCategoryId(Long categoryId);
}
