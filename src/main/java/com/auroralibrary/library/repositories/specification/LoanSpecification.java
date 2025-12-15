package com.auroralibrary.library.repositories.specification;

import com.auroralibrary.library.model.Loan;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class LoanSpecification {

    public static Specification<Loan> hasIsCompleted(Boolean isCompleted){
        return (root, query, criteriaBuilder)
                -> isCompleted != null ? criteriaBuilder.equal(root.get("isCompleted"), isCompleted) : null;
    }

    public static Specification<Loan> hasDate(LocalDate loanDate){
        return (root, query, criteriaBuilder)
                -> loanDate != null ? criteriaBuilder.equal(root.get("loanDate"), loanDate) : null;
    }

    public static Specification<Loan> hasName(String name) {
        return (root, query, criteriaBuilder)
        -> name != null ? criteriaBuilder.like(root.join("client").get("name"), "%" + name + "%") : null;
    }

    public static Specification<Loan> hasCpf(String cpf) {
        return (root, query, criteriaBuilder)
                -> cpf != null ? criteriaBuilder.equal(root.join("client").get("cpf"), cpf) : null;
    }

    public static Specification<Loan> hasTitle(String title){
        return (root, query, criteriaBuilder)
        -> title != null ? criteriaBuilder.like(root.join("book").get("title"), "%" + title + "%") : null;
    }

    public static Specification<Loan> hasPublisher(String publisher) {
        return (root, query, criteriaBuilder)
        -> publisher != null ? criteriaBuilder.like(root.join("book").get("publisher"), "%" + publisher + "%") : null;
    }
}
