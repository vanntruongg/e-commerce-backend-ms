package com.vantruong.book.repository;

import com.vantruong.book.entity.Book;
import org.springframework.data.domain.Limit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
//  List<Book> findByCategoryIdsContains(String categoryId);
//  List<Book> findByCategoryIdsContains(String categoryId, Limit limit);

  List<Book> findProductByTitleContainingIgnoreCase(String trim, Limit of);

}
