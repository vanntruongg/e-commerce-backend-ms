package com.vantruong.book.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "books")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
  @Id
  String id;
  String title;
  double price;
  int stock;
  List<String> imageUrls;
  String language;
  String publisher;
  String description;
  @DBRef
  Author author;
  @DBRef
  List<Category> categories;
}
