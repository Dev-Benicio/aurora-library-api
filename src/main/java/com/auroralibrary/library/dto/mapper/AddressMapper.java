package com.auroralibrary.library.dto.mapper;

import org.springframework.stereotype.Component;
import com.auroralibrary.library.dto.response.AddressResponse;
import com.auroralibrary.library.model.Address;
import com.auroralibrary.library.validation.StringFormatter;

@Component
public class AddressMapper {

  public static AddressResponse toResponse(Address address) {
    return new AddressResponse(
      address.getIdAddress(),
      address.getCep(),
      address.getNumber(),
      address.getState(),
      address.getCity(), 
      address.getNeighborhood(),
      address.getStreet()
    );
  }

  public static Address toResponseAddress(AddressResponse response) {
    return Address.builder()
        .idAddress(response.id())
        .cep(StringFormatter.removeSpecialCharacters(response.cep()))
        .number(response.number())
        .state(response.state())
        .city(response.city())
        .neighborhood(response.neighborhood())
        .street(response.street())
        .build();
  }
}
