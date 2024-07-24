package com.vantruong.book.dto.response;

import com.vantruong.book.entity.Author;
import com.vantruong.book.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BookResponse {
  String id;
  String title;
  Author author;
  double price;
  int stock;
  List<String> imageUrls;
  String language;
  String publisher;
  String description;
  List<Category> categories;
}
