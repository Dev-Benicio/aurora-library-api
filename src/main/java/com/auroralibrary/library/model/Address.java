package com.auroralibrary.library.model;

import com.auroralibrary.library.validation.StringFormatter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idAddress;

  @Column(nullable = false, length = 8)
  private String cep;

  @Column(nullable = false)
  private Integer number;

  @Column(nullable = false)
  private String state;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String neighborhood;

  @Column(nullable = false)
  private String street;

  public void atribuiValoresCreate(String cep) { this.cep = StringFormatter.removeSpecialCharacters(cep); }

  public void atribuiValoresUpdate(Address address) { this.cep = StringFormatter.removeSpecialCharacters(address.cep); }
}
