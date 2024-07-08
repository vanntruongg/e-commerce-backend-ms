package productservice.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import productservice.entity.Category;
import productservice.entity.Product;

import java.util.List;

@Builder
@Getter
@Setter
public class ProductResponse {
  private Product product;
  private List<Category> categories;
}
