package com.auroralibrary.library.model;

import com.auroralibrary.library.validation.CpfValidator;
import com.auroralibrary.library.validation.StringFormatter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
@Table(name = "client")
public class Client {
    /* Define a PK */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private Long idClient;

    /* Define as Colunas */
    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 11)
    private String phone;
    
    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 11)
    private String cpf;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;

    public void atribuiValoresCreate(String cpf, String phone, Address address) {
        this.phone = StringFormatter.removeSpecialCharacters(phone);
        this.cpf = StringFormatter.removeSpecialCharacters(cpf);
        this.address = address;
    }

    public void atribuiValoresUpdate(String phone, Address address) {
        this.phone = phone;
        this.address = address;
    }
}
