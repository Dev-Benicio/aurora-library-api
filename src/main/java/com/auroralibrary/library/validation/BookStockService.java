package com.auroralibrary.library.validation;

import com.auroralibrary.library.dto.request.LoanCreateRequest;
import com.auroralibrary.library.dto.request.LoanUpdateRequest;
import com.auroralibrary.library.repositories.LoanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.auroralibrary.library.dto.mapper.BookMapper;
import com.auroralibrary.library.dto.request.BookUpdateRequest;
import com.auroralibrary.library.dto.response.BookResponse;
import com.auroralibrary.library.model.Book;
import com.auroralibrary.library.model.Loan;
import com.auroralibrary.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class BookStockService implements InterfaceValidationLoan {
    @Autowired
    private BookService bookService;

    @Autowired
    private LoanRepository loanRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void validateCreate(LoanCreateRequest loan) {
        BookResponse bookResponse = bookService.findById(loan.idBook());
        Book book = BookMapper.toEntity(bookResponse);

        if (book.getQuantityBooks() > 0) {
            Long updatedQuantity = book.getQuantityBooks() - 1;
            book.setQuantityBooks(updatedQuantity);
            BookUpdateRequest update = BookMapper.BookToUpdateRequest(book);
            bookService.update(update, loan.idBook());
        }
    }

    @Override
    public void validateUpdate(LoanUpdateRequest loan, Long id) {
        Loan getLoan = loanRepository.getReferenceById(id);
        BookResponse bookResponse = bookService.findById(getLoan.getBook().getIdBook());
        Book book = BookMapper.toEntity(bookResponse);

        if (getLoan.getIsCompleted() != loan.isCompleted()){
            if (loan.isCompleted()) {
                Long updatedQuantity = book.getQuantityBooks() + 1;
                book.setQuantityBooks(updatedQuantity);
                BookUpdateRequest update = BookMapper.BookToUpdateRequest(book);
                bookService.update(update, getLoan.getBook().getIdBook());
            }
        }
    }
}