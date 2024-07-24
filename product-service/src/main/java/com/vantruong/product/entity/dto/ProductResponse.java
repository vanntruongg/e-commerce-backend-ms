package com.vantruong.product.entity.dto;

import com.vantruong.product.entity.Category;
import com.vantruong.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ProductResponse {
  private Product product;
  private List<Category> categories;
}
