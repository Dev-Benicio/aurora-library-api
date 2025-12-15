package com.auroralibrary.library.dto.mapper;

import java.math.BigDecimal;

import com.auroralibrary.library.model.Client;
import org.springframework.stereotype.Component;
import com.auroralibrary.library.dto.request.LoanCreateRequest;
import com.auroralibrary.library.dto.response.LoanBookResponse;
import com.auroralibrary.library.dto.response.LoanResponse;
import com.auroralibrary.library.dto.response.LoanClientResponse;
import com.auroralibrary.library.model.Book;
import com.auroralibrary.library.model.Loan;

@Component
public class LoanMapper {
  public static Loan LoanCreateMapper(LoanCreateRequest loanCreateRequest) {
    return Loan.builder()
      .idLoan(null)
      .client(Client.builder()
        .idClient(loanCreateRequest.idClient())
        .build()
      )
      .book(Book.builder()
        .idBook(loanCreateRequest.idBook())
        .build()
      )
      .loanDate(loanCreateRequest.loanDate())
      .limitDate(loanCreateRequest.limitDate())
      .isCompleted(false)
        .fineStatus(false)
        .penalty(BigDecimal.ZERO)
        .build();
  }

  public static LoanResponse toResponse(Loan loan) {
    return new LoanResponse(
      loan.getIdLoan(),
      loan.getLoanDate(),
      loan.getLimitDate(),
      loan.getDeliveryDate(),
      loan.getPenalty(),
      loan.getIsCompleted(),
      loan.getFineStatus(),
      toLoanClientResponse(loan.getClient()),
      toLoanBookResponse(loan.getBook()));
  }

  public static LoanClientResponse toLoanClientResponse(Client client) {
    return new LoanClientResponse(
      client.getIdClient(),
      client.getName(),
      client.getCpf()
    );
  }

  public static LoanBookResponse toLoanBookResponse(Book book){
    return new LoanBookResponse(
      book.getIdBook(),
      book.getTitle(),
      book.getBookCover()
    );
  }
}
