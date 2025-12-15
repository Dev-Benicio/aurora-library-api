package com.auroralibrary.library.repositories.specification;

import com.auroralibrary.library.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> hasCategory(String category){
        return (root, query, criteriaBuilder)
                -> category != null ? criteriaBuilder.equal(root.get("category"), category) : null;
    }

    public static Specification<Book> hasTitle(String title) {
        return (root, query, criteriaBuilder)
                -> title != null ? criteriaBuilder.like(root.get("title"), "%" + title + "%") : null;
    }

    public static Specification<Book> hasPublisher(String publisher) {
        return (root, query, criteriaBuilder)
                -> publisher != null ? criteriaBuilder.like(root.get("publisher"), "%" + publisher + "%") : null;
    }
}
