package com.auroralibrary.library.validation;

import com.auroralibrary.library.dto.request.LoanCreateRequest;
import com.auroralibrary.library.dto.request.LoanUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auroralibrary.library.model.Loan;
import com.auroralibrary.library.repositories.LoanRepository;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

@Service
public class FineCalculationService implements InterfaceValidationLoan {

    @Autowired
    private LoanRepository loanRepository;

    public void validateCreate(LoanCreateRequest loan) {
    }

    @Override
    public void validateUpdate(LoanUpdateRequest loan, Long id) {

        Loan getLoan = loanRepository.getReferenceById(id);

        if (loan.deliveryDate() == null) {
            getLoan.setPenalty(BigDecimal.ZERO);
            loanRepository.save(getLoan);
        } else if (loan.deliveryDate().isEqual(getLoan.getLimitDate())
                || loan.deliveryDate().isBefore(getLoan.getLimitDate())
        ) {
            getLoan.setPenalty(BigDecimal.ZERO);
            loanRepository.save(getLoan);
        } else {
            long daysBetween = ChronoUnit.DAYS.between(getLoan.getLimitDate(), loan.deliveryDate());
            BigDecimal valuefinal = BigDecimal.valueOf(daysBetween).multiply(BigDecimal.valueOf(0.75));
            getLoan.setPenalty(valuefinal);
            loanRepository.save(getLoan);
        }
    }
}