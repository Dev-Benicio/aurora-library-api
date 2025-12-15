package com.auroralibrary.library.dto.response;

import lombok.Data;

@Data
public class CepResponse {
  private String cep;
  private String state;
  private String city;
  private String neighborhood;
  private String street;
}
