package com.auroralibrary.library.validation;

import com.auroralibrary.library.dto.request.LoanCreateRequest;
import com.auroralibrary.library.dto.request.LoanUpdateRequest;
import com.auroralibrary.library.exception.LoanLimitExceededException;
import com.auroralibrary.library.repositories.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ValidateCountLoanClient implements InterfaceValidationLoan {
    @Autowired
    private LoanRepository loanRepository;

    public void validateCreate(LoanCreateRequest loan) {
        Long count = loanRepository.countByClientId(loan.idClient());
        if (count >= 3) {
            log.info("Cliente atingiu limite máximo de empréstimos");
            throw new LoanLimitExceededException("Clienteário atingiu limite máximo de empréstimos");
        }
    }

    @Override
    public void validateUpdate(LoanUpdateRequest loanUpdateRequest, Long id) {
    }
}
