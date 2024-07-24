package com.vantruong.book.service;

import com.vantruong.book.dto.request.BooKRequest;
import com.vantruong.book.dto.response.BookResponse;
import com.vantruong.book.entity.Book;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface BookService {
  List<Book> findAllBookByCategoryId(String categoryId);
  List<Book> findAllBookByCategoryId(String categoryId, int limit);
  Book findById(String id);
  Page<Book> findAllBook(String categoryId, String order, int pageNo, int pageSize);

  int getStockById(String id);

  BookResponse getProductById(String id);

  Long getTotalBooksCount();

  List<Book> getProductByName(String name, int limit);

  BookResponse createProduct(BooKRequest request);

  Boolean updateProduct(BooKRequest request);

  List<Book> getProductsByIds(List<String> productIds);

  Boolean updateProductQuantityByOrder(Map<String, Integer> stockUpdate);
}
