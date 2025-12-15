package com.auroralibrary.library.model;

import java.time.Year;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Define que é modelo da Tabela "book"(BD) */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBook;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, length = 100)
    private String publisher;

    @Column(nullable = false, length = 4)
    private Year year;

    @Column(nullable = false)
    private Long quantityBooks;

    @Column(nullable = false)
    private String bookCover;
}