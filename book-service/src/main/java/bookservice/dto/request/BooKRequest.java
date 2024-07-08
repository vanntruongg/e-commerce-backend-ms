package bookservice.dto.request;

import bookservice.entity.Author;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BooKRequest {
  String id;
  String title;
  Author author;
  double price;
  int stock;
  List<String> imageUrls;
  String language;
  String publisher;
  String description;
  List<String> categoryIds;
}
