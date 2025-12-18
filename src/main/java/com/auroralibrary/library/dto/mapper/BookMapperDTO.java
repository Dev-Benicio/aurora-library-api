package com.auroralibrary.library.dto.mapper;

import com.auroralibrary.library.dto.request.BookCreateRequest;
import com.auroralibrary.library.dto.request.BookUpdateRequest;
import com.auroralibrary.library.dto.response.BookResponse;
import com.auroralibrary.library.validation.ValidationBookResponse;
import com.auroralibrary.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapperDTO {

  Book createToEntity(BookCreateRequest bookCreateRequest);

  Book updateToEntity(@MappingTarget Book book, BookUpdateRequest bookUpdateRequest);

  @Mapping(target = "idBook", source = "id")
  Book responseToEntity(BookResponse response);

  @Mapping(target = "id", source = "idBook")
  @Mapping(target = "bookCover", source = "book")
  BookResponse bookToResponse(Book book);

  BookUpdateRequest bookToUpdateRequest(Book book);

  default String bookCover(Book book) {
    return ValidationBookResponse.directoryBookCover(book.getBookCover());
  }
}
