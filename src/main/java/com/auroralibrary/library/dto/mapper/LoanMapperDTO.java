package com.auroralibrary.library.dto.mapper;

import com.auroralibrary.library.dto.request.LoanCreateRequest;
import com.auroralibrary.library.dto.response.LoanBookResponse;
import com.auroralibrary.library.dto.response.LoanClientResponse;
import com.auroralibrary.library.dto.response.LoanResponse;
import com.auroralibrary.library.model.Book;
import com.auroralibrary.library.model.Client;
import com.auroralibrary.library.model.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanMapperDTO {

  Loan loanCreateToEntity(LoanCreateRequest loanCreateRequest);

  @Mapping(target = "client", source = "client")
  @Mapping(target = "book", source = "book")
  LoanResponse loanToResponse(Loan loan);

  @Mapping(target = "id", source = "idClient")
  LoanClientResponse clientToLoanClientResponse(Client client);

  @Mapping(target = "id", source = "idBook")
  LoanBookResponse bookToLoanBookResponse(Book book);
}
