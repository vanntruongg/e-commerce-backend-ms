package com.vantruong.common.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@Setter
public class CategoryResponse {

  private int id;

  private String name;

  private String image;

  private CategoryResponse parentCategory;
}
