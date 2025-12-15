package com.auroralibrary.library.dto.mapper;

import org.springframework.stereotype.Component;
import com.auroralibrary.library.dto.request.ClientUpdateRequest;
import com.auroralibrary.library.dto.response.ClientResponse;
import com.auroralibrary.library.model.Client;

@Component
public class ClientMapper {

  public static Client clientUpdateMapper(ClientResponse client, ClientUpdateRequest clientRequest
) {
    return Client.builder()
      .idClient(client.id())
      .name(clientRequest.name())
      .phone(clientRequest.phone())
      .email(clientRequest.email())
      .cpf(client.cpf())
      .build();
  }

  public static ClientResponse toResponse(Client client) {
    return new ClientResponse(
      client.getIdClient(),
      client.getName(),
      client.getPhone(),
      client.getEmail(),
      client.getCpf(),
      AddressMapper.toResponse(client.getAddress())
    );
  }
}
