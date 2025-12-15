package com.auroralibrary.library.service;

import com.auroralibrary.library.dto.mapper.BookMapperDTO;
import com.auroralibrary.library.repositories.specification.BookSpecification;
import com.auroralibrary.library.validation.SaveImages;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.auroralibrary.library.dto.mapper.BookMapper;
import com.auroralibrary.library.dto.request.BookCreateRequest;
import com.auroralibrary.library.dto.request.BookUpdateRequest;
import com.auroralibrary.library.dto.response.BookResponse;
import com.auroralibrary.library.exception.DatabaseException;
import com.auroralibrary.library.exception.InvalidParameterException;
import com.auroralibrary.library.exception.ResourceNotFoundException;
import com.auroralibrary.library.model.Book;
import com.auroralibrary.library.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/* É uma classe Serviço,
que define como o processamento deve ser realizado (CRUD) */
@Slf4j
@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapperDTO bookMapperDTO;

    @Autowired
    private SaveImages saveImages;

    private final String folder = "/BookCovers/";

    /* Métodos para o CRUD */
    @Transactional
    public BookResponse create(BookCreateRequest request, MultipartFile image) {
        try {
            String fileName = saveImages.saveImage(image, folder);

            Book book = BookMapper.BookCreateMapper(request);
            book.setBookCover(fileName);
            Book savedBook = bookRepository.save(book);

            log.info("Livro criado com sucesso: ID {}", savedBook.getIdBook());

            return bookMapperDTO.bookToResponse(savedBook);

        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao criar livro: {}", e.getMessage());
            throw new DatabaseException("Erro ao criar livro");
        }
    }

    public Page<BookResponse> findAll(Pageable pageable) {
        Page<Book> books = bookRepository.findAll(pageable);

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum livro encontrado");
        }

        return books.map(bookMapperDTO::bookToResponse);
    }

    public BookResponse findById(Long id_book) {
        try {
            Book book = bookRepository.findById(id_book)
                    .orElseThrow(() -> {
                        log.error("Livro com ID {} não encontrado", id_book);
                        return new ResourceNotFoundException("Livro não encontrado");
                    });

            log.info("Livro encontrado com sucesso: ID {}", id_book);
            return bookMapperDTO.bookToResponse(book);

        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao buscar livro, ID inválido: {}", e.getMessage());
            throw new DatabaseException("Erro ao buscar livro, ID inválido");
        } catch (IllegalArgumentException e) {
            log.error("Erro ao buscar livro, ID inválido: {}", e.getMessage());
            throw new InvalidParameterException("ID inválido");
        }
    }

    public Page<BookResponse> findByFilters(String title, String category, Pageable pageable) {
        Specification<Book> bookSpecification = Specification
                .where(BookSpecification.hasCategory(category))
                .and(BookSpecification.hasTitle(title));

        Page<Book> books = bookRepository.findAll(bookSpecification, pageable);

        return books.map(bookMapperDTO::bookToResponse);
    }

    @Transactional
    public BookResponse update(BookUpdateRequest request, Long id_book) {
        try {
            Book book = bookRepository.findById(id_book)
                    .orElseThrow(() -> {
                        log.error("Livro com ID {} não encontrado", id_book);
                        return new ResourceNotFoundException("Livro não encontrado");
                    });

            Book bookMapper = BookMapper.BookUpdateMapper(book, request);
            Book savedBook = bookRepository.save(bookMapper);
            log.info("Livro atualizado com sucesso - ID: {}", savedBook.getIdBook());

            return bookMapperDTO.bookToResponse(savedBook);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao atualizar livro - Body: {}", e.getMessage());  
            throw new DatabaseException("Erro ao atualizar livro");
        }
    }

    public void delete(Long id_book) {
        try {
            findById(id_book);
            bookRepository.deleteById(id_book);
            log.info("Livro excluído com sucesso: ID {}", id_book);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao excluir livro");
            throw new DatabaseException("Erro ao excluir livro");
        }
    }
}
