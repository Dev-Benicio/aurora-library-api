package com.auroralibrary.library.validation;

import com.auroralibrary.library.dto.request.LoanCreateRequest;
import com.auroralibrary.library.dto.request.LoanUpdateRequest;
import org.springframework.stereotype.Service;

@Service
public interface InterfaceValidationLoan {
    void validateCreate(LoanCreateRequest loanCreateRequest);

    void validateUpdate(LoanUpdateRequest loanUpdateRequest, Long id);
}
