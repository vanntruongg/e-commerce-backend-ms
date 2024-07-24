package com.vantruong.book;

import com.vantruong.book.repository.AuthorRepository;
import com.vantruong.book.repository.BookRepository;
import com.vantruong.book.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
@EnableDiscoveryClient
public class BookServiceApplication implements CommandLineRunner {
  private final BookRepository bookRepository;
  private final CategoryRepository categoryRepository;
  private final AuthorRepository authorRepository;

  public static void main(String[] args) {
    SpringApplication.run(BookServiceApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
//    Optional<Category> category = categoryRepository.findById("6672dfc8c6bdc354761154f2");
//    Optional<Category> category2 = categoryRepository.findById("6672e004e9b326da1b07c353");
//    Optional<Category> toeic = categoryRepository.findById("6672e099e9b326da1b07c354");
//    Optional<Category> ielts = categoryRepository.findById("6672e0abe9b326da1b07c355");
//
//    Category category1 = Category.builder()
//            .category("Sách luyện thi")
//            .subCategories(List.of(toeic.get(), ielts.get()))
//            .build();
//    categoryRepository.save(category1);
//
//    Author author = Author.builder()
//            .name("Craig Walls")
//            .biography("Craig Walls is a principal software engineer at Pivotal," +
//                    " a popular author, an enthusiastic supporter of Spring Framework and voice-first applications, and a frequent conference speaker")
//            .build();
//    authorRepository.save(author);
//    Book book = Book.builder()
//            .title("Spring in Action")
//            .author(author)
//            .price(186.000)
//            .stock(10)
//            .imageUrls(List.of("https://img.lazcdn.com/g/shop/6af79fd9c27a4e29e5f5adf0d4288a07.png_2200x2200q80.png_.webp"))
//            .description("Spring Framework has been making Java developers more productive and successful for over a dozen years, and it shows no signs of slowing down!\n" +
//                    "\n" +
//                    "Spring in Action, 5th Edition is the fully-updated revision of Manning's bestselling Spring in Action. This new edition includes all Spring 5.0 updates, along with new examples on reactive programming, Spring WebFlux, and microservices. Readers will also find the latest Spring best practices, including Spring Boot for application setup and configuration.")
//            .categories(List.of(category.get(), category2.get()))
//            .language("Tiếng Anh")
//            .publisher("Manning Publications")
//            .build();
//    bookRepository.save(book);
  }
}
