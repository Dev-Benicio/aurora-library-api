package com.auroralibrary.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.math.BigDecimal;

/* Definem que é uma classe Modelo da Tabela "loan"(BD) */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "loan")
public class Loan {
    /* Define que essa é a PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLoan;

    /* Define as colunas na Tabela */
    @Column(nullable = false, length = 8)
    private LocalDate loanDate;
    
    @Column(nullable = false, length = 8)
    private LocalDate limitDate;
    
    @Column(length = 8)
    private LocalDate deliveryDate;

    private BigDecimal penalty;

    private boolean isCompleted;

    private boolean fineStatus;
    
    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "id_book", nullable = false)
    private Book book;

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean getFineStatus() {
        return fineStatus;
    }

    public void setFineStatus(boolean fineStatus) {
        this.fineStatus = fineStatus;
    }

    public void atribuiValoresCreate(Book book, Client client) {
        this.book = book;
        this.client = client;
    }

    public void atribuiValoresUpdate(LocalDate deliveryDate, boolean isCompleted, boolean fineStatus) {
        this.deliveryDate = deliveryDate;
        this.isCompleted = isCompleted;
        this.fineStatus = fineStatus;
    }
}
