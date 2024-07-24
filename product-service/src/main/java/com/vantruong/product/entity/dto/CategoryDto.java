package com.vantruong.product.entity.dto;

import lombok.Getter;
import com.vantruong.product.entity.Category;

@Getter
public class CategoryDto {

  private int id;

  private String name;

  private String image;

  private Category parentCategory;
}
