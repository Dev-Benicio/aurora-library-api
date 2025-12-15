package com.auroralibrary.library.service;

import com.auroralibrary.library.repositories.specification.LoanSpecification;
import com.auroralibrary.library.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.auroralibrary.library.dto.mapper.LoanMapper;
import com.auroralibrary.library.dto.request.LoanCreateRequest;
import com.auroralibrary.library.dto.request.LoanUpdateRequest;
import com.auroralibrary.library.dto.response.LoanResponse;
import com.auroralibrary.library.exception.DatabaseException;
import com.auroralibrary.library.exception.ResourceNotFoundException;
import com.auroralibrary.library.model.*;
import com.auroralibrary.library.repositories.BookRepository;
import com.auroralibrary.library.repositories.LoanRepository;
import com.auroralibrary.library.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanService {
    /* Faz toda a importação das dependências nescessárias para o CRUD */
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private List<InterfaceValidationLoan> validations;

    @Autowired
    private BookRepository bookRepository;

    /* Métodos para o CRUD */
    @Transactional
    public LoanResponse create(LoanCreateRequest loanCreateRequest) {
        try {
            Loan loan = LoanMapper.LoanCreateMapper(loanCreateRequest);
            Client client = clientRepository.findById(loan.getClient().getIdClient())
                    .orElseThrow(() -> new ResourceNotFoundException("Clienteário não encontrado"));
            Book book = bookRepository.findById(loan.getBook().getIdBook())
                    .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));

            loan.atribuiValoresCreate(book, client);

            validations.forEach(v -> v.validateCreate(loanCreateRequest));

            Loan savedLoan = loanRepository.save(loan);
            return LoanMapper.toResponse(savedLoan);

        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao registrar empréstimo, dados duplicados ou inválidos, Error: {}", e.getMessage());
            throw new DatabaseException("Erro ao registrar empréstimo, dados duplicados ou inválidos");
        }
    }

    public Page<LoanResponse> findAll(Pageable pageable) {
        Page<Loan> loans = loanRepository.findAll(pageable);

        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum emprestimo encontrado");
        }

        return loans.map(LoanMapper::toResponse);
    }

    public LoanResponse findById(Long id_loan) {
        try {
            Loan loan = loanRepository.findById(id_loan)
                    .orElseThrow(() -> {
                        log.error("Empréstimo com ID {} não encontrado", id_loan);
                        return new ResourceNotFoundException("Empréstimo não encontrado");
                    });

            log.info("Empréstimo encontrado com sucesso: ID {}", id_loan);
            return LoanMapper.toResponse(loan);

        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao buscar empréstimo, ID inválido: {}", e.getMessage());
            throw new DatabaseException("Erro ao buscar empréstimo, ID inválido");
        }
    }

    public Page<LoanResponse> findByFilters(Boolean isCompleted, LocalDate loanDate, String name, String cpf, String title, String publisher, Pageable pageable){
        Specification<Loan> loanSpecification = Specification
                .where(LoanSpecification.hasIsCompleted(isCompleted))
                .and(LoanSpecification.hasDate(loanDate))
                .and(LoanSpecification.hasName(name))
                .and(LoanSpecification.hasCpf(cpf))
                .and(LoanSpecification.hasTitle(title))
                .and(LoanSpecification.hasPublisher(publisher));

        Page<Loan> loans = loanRepository.findAll(loanSpecification, pageable);

        return loans.map(LoanMapper::toResponse);
    }

    @Transactional
    public LoanResponse update(LoanUpdateRequest loanUpdateRequest, Long id_loan) {
        try {
            Loan loan = loanRepository.getReferenceById(id_loan);

            validations.forEach(v -> v.validateUpdate(loanUpdateRequest, id_loan));

            loan.atribuiValoresUpdate(
                loanUpdateRequest.deliveryDate(), loanUpdateRequest.isCompleted(), loanUpdateRequest.fineStatus()
            );

            Loan savedLoan = loanRepository.save(loan);
            return LoanMapper.toResponse(savedLoan);

        } catch (DataIntegrityViolationException e ){
            log.error("Erro ao atualizar empréstimo");
            throw new DatabaseException("Erro ao atualizar empréstimo");
        }
    }

    public void delete(Long id_loan) {
        try {
            findById(id_loan);
            loanRepository.deleteById(id_loan);
            log.info("Empréstimo excluído com sucesso: ID {}", id_loan);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao excluir empréstimo");
            throw new DatabaseException("Erro ao excluir empréstimo");
        }
    }
}
