package com.auroralibrary.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auroralibrary.library.model.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {
  @Query("SELECT COUNT(l) FROM Loan l WHERE l.client.idClient = :idClient AND l.isCompleted = false")
  Long countByClientId(@Param("idClient") Long idClient);
}
