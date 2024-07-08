package bookservice.repository;

import bookservice.entity.Book;
import bookservice.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
  List<Category> findByIdIn(List<String> ids);
}
