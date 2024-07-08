package productservice.entity.dto;

import lombok.*;
import productservice.entity.Category;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
  private Category category;
  private List<CategoryResponse> subCategories;
}
