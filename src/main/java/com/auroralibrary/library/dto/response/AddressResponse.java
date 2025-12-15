package com.auroralibrary.library.dto.response;


public record AddressResponse(
  Long id,
  String cep,
  Integer number,
  String state,
  String city,
  String neighborhood,
  String street
) {
}
