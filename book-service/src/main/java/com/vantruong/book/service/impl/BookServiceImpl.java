package com.vantruong.book.service.impl;

import com.vantruong.book.constant.MessageConstant;
import com.vantruong.book.dto.request.BooKRequest;
import com.vantruong.book.dto.response.BookResponse;
import com.vantruong.book.entity.Book;
import com.vantruong.book.entity.Category;
import com.vantruong.book.exception.ErrorCode;
import com.vantruong.book.exception.NotFoundException;
import com.vantruong.book.repository.BookRepository;
import com.vantruong.book.repository.CategoryRepository;
import com.vantruong.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
  private final BookRepository bookRepository;
  private final static int PAGE_SIZE = 12;
  private final CategoryRepository categoryRepository;


  @Override
  public List<Book> findAllBookByCategoryId(String categoryId) {
//    return bookRepository.findByCategoryIdsContains(categoryId);
  return null;
  }


  @Override
  public List<Book> findAllBookByCategoryId(String categoryId, int limit) {
//    return bookRepository.findByCategoryIdsContains(categoryId, Limit.of(limit));
    return null;

  }

  @Override
  public Book findById(String id) {
    return bookRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.BOOK_NOT_FOUND));
  }

  private Pageable createPagingAndSort(String order, int pageNo, int pageSize) {
    pageNo = Math.max(0, pageNo - 1);
    Sort sort = Sort.unsorted();

    if (order != null && (order.equalsIgnoreCase("desc") || order.equalsIgnoreCase("asc"))) {
      sort = Sort.by(Sort.Direction.fromString(order.toUpperCase()), "price");
    }

    if (pageSize == 0)
      pageSize = PAGE_SIZE;

    return PageRequest.of(pageNo, pageSize, sort);
  }

  @Override
  public Page<Book> findAllBook(String categoryId, String order, int pageNo, int pageSize) {
    if (!Objects.equals(categoryId, "")) {
      List<Book> productList = findAllBookByCategoryId(categoryId);
      Pageable pageable = createPagingAndSort(order, pageNo, pageSize);

      // sort list product before using paging because PageImpl not sort
      List<Book> sortedList = sortList(productList, order);
      int first = Math.min(Long.valueOf(pageable.getOffset()).intValue(), sortedList.size());
      int last = Math.min(first + pageable.getPageSize(), sortedList.size());

      return new PageImpl<>(sortedList.subList(first, last), pageable, sortedList.size());
    }
    return bookRepository.findAll(createPagingAndSort(order, pageNo, pageSize));
  }

  @Override
  public int getStockById(String id) {
    Book book = findById(id);
    return book.getStock();
  }

  @Override
  public BookResponse getProductById(String id) {
    Book book = findById(id);
    return mapToBookResponse(book);
  }

  @Override
  public Long getTotalBooksCount() {
    return bookRepository.count();
  }

  @Override
  public List<Book> getProductByName(String name, int limit) {
    return bookRepository.findProductByTitleContainingIgnoreCase(name.trim(), Limit.of(limit));
  }

  @Override
  public BookResponse createProduct(BooKRequest request) {
    Book book = new Book();
    mapToBook(book, request);
    Book bookSaved = bookRepository.save(book);
    return mapToBookResponse(bookSaved);
  }

  @Override
  public Boolean updateProduct(BooKRequest request) {
    Book book = findById(request.getId());
    mapToBook(book, request);
    bookRepository.save(book);
    return true;
  }

  @Override
  public List<Book> getProductsByIds(List<String> bookIds) {
    return bookRepository.findAllById(bookIds);
  }

  private List<Book> sortList(List<Book> books, String order) {
    // if order empty return list product not sort
    if (order.isEmpty()) {
      return books;
    }
    Comparator<Book> comparator = Comparator.comparing(Book::getPrice);
    if (order.equalsIgnoreCase("desc")) {
      comparator = comparator.reversed();
    }
    return books.stream()
            .sorted(comparator)
            .collect(Collectors.toList());
  }

  @Override
  public Boolean updateProductQuantityByOrder(Map<String, Integer> stockUpdate) {
    List<Book> books = new ArrayList<>();
    for (Map.Entry<String, Integer> productIdAndQuantity : stockUpdate.entrySet()) {
      String bookId = productIdAndQuantity.getKey();
      int quantity = productIdAndQuantity.getValue();

      Book book = findById(bookId);
      int newQuantity = book.getStock() - quantity;
      if (newQuantity < 0) {
        return false;
      } else {
        book.setStock(newQuantity);
        books.add(book);
      }
    }
    bookRepository.saveAll(books);
    return true;
  }

  private BookResponse mapToBookResponse(Book book) {
    return BookResponse.builder()
            .id(book.getId())
            .title(book.getTitle())
            .author(book.getAuthor())
            .price(book.getPrice())
            .imageUrls(book.getImageUrls())
            .language(book.getLanguage())
            .publisher(book.getPublisher())
            .description(book.getDescription())
            .stock(book.getStock())
            .categories(book.getCategories())
            .build();
  }

  private void mapToBook(Book book, BooKRequest booKRequest) {
    List<Category> categories= categoryRepository.findByIdIn(booKRequest.getCategoryIds());
    book.setTitle(booKRequest.getTitle());
    book.setAuthor(booKRequest.getAuthor());
    book.setPrice(booKRequest.getPrice());
    book.setImageUrls(booKRequest.getImageUrls());
    book.setLanguage(booKRequest.getLanguage());
    book.setPublisher(booKRequest.getPublisher());
    book.setDescription(booKRequest.getDescription());
    book.setStock(booKRequest.getStock());
    book.setCategories(categories);
  }
}
