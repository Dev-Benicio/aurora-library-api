package com.auroralibrary.library.dto.mapper;

import org.springframework.stereotype.Component;
import com.auroralibrary.library.dto.request.BookCreateRequest;
import com.auroralibrary.library.dto.request.BookUpdateRequest;
import com.auroralibrary.library.dto.response.BookResponse;
import com.auroralibrary.library.model.Book;

import static com.auroralibrary.library.validation.ValidationBookResponse.directoryBookCover;

@Component
public class BookMapper {
  public static Book BookCreateMapper(BookCreateRequest bookCreateRequest) {
    return Book.builder()
      .idBook(null)
      .title(bookCreateRequest.title())
      .category(bookCreateRequest.category())
      .author(bookCreateRequest.author())
      .publisher(bookCreateRequest.publisher())
      .year(bookCreateRequest.year())
      .quantityBooks(bookCreateRequest.quantityBooks())
      .build();
  }

  public static Book BookUpdateMapper(Book book, BookUpdateRequest bookUpdateRequest) {
    return Book.builder()
      .idBook(book.getIdBook())
      .title(bookUpdateRequest.title())
      .category(bookUpdateRequest.category())
      .author(bookUpdateRequest.author())
      .publisher(bookUpdateRequest.publisher())
      .year(bookUpdateRequest.year())
      .quantityBooks(bookUpdateRequest.quantityBooks())
      .bookCover(bookUpdateRequest.bookCover())
      .build();
  }

  public static Book toEntity(BookResponse response) {
    return Book.builder()
        .idBook(response.id())
        .title(response.title())
        .category(response.category())
        .author(response.author())
        .publisher(response.publisher())
        .year(response.year())
        .quantityBooks(response.quantityBooks())
        .bookCover(response.bookCover())
        .build();
  }

  public static BookUpdateRequest BookToUpdateRequest(Book book) {
    return new BookUpdateRequest(
      book.getTitle(),
      book.getCategory(),
      book.getAuthor(),
      book.getPublisher(),
      book.getYear(),
      book.getQuantityBooks(),
      book.getBookCover()
    );
  }
}
