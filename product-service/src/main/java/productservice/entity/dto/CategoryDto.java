package productservice.entity.dto;

import lombok.Getter;
import productservice.entity.Category;

@Getter
public class CategoryDto {

  private int id;

  private String name;

  private String image;

  private Category parentCategory;
}
