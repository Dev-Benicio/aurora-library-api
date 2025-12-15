package com.auroralibrary.library.validation;

import com.auroralibrary.library.dto.request.LoanCreateRequest;
import com.auroralibrary.library.dto.request.LoanUpdateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auroralibrary.library.exception.ValidationException;
import com.auroralibrary.library.model.Loan;
import com.auroralibrary.library.repositories.LoanRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DateLoanService implements InterfaceValidationLoan {

    @Autowired
    private LoanRepository loanRepository;

    public void validateCreate(LoanCreateRequest loan) {
        if (loan.limitDate().isBefore(loan.loanDate())) {
            log.error("Data de entrega não pode ser anterior à data do empréstimo");
            throw new ValidationException("Data de entrega não pode ser anterior à data do empréstimo");
        }
    }

    @Override
    public void validateUpdate(LoanUpdateRequest loan, Long id) {

        Loan getLoan = loanRepository.getReferenceById(id);

        if (loan.deliveryDate() != null) {
            if (loan.deliveryDate().isBefore(getLoan.getLoanDate())) {
                log.error("Data de devolução não pode ser anterior à data do empréstimo");
                throw new ValidationException("Data de devolução não pode ser anterior à data do empréstimo");
            }
        }
        if (loan.deliveryDate() == null && loan.isCompleted()){
            throw new ValidationException("Devolução não pode ser concluída sem a data de devolução");
        }
    }
}