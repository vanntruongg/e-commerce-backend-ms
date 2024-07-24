package com.vantruong.product.specification;

import com.vantruong.product.entity.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecification {

  public Specification<Product> hasCategory(int categoryId) {
    return ((root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("category").get("id"), categoryId)
    );
  }
}
