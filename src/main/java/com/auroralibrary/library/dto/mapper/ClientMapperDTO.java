package com.auroralibrary.library.dto.mapper;

import com.auroralibrary.library.dto.request.ClientCreateRequest;
import com.auroralibrary.library.dto.request.ClientUpdateRequest;
import com.auroralibrary.library.dto.response.ClientResponse;
import com.auroralibrary.library.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AddressMapperDTO.class})
public interface ClientMapperDTO {

    @Mapping(target = "address", ignore = true)
    Client clientToCreate(ClientCreateRequest clientRequest);

    @Mapping(target = "cpf", source = "client.cpf")
    @Mapping(target = "idClient", source = "client.id")
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "name", source = "clientRequest.name")
    @Mapping(target = "email", source = "clientRequest.email")
    @Mapping(target = "phone", source = "clientRequest.phone")
    Client updateToEntity(ClientResponse client, ClientUpdateRequest clientRequest);

    @Mapping(target = "id", source = "idClient")
    ClientResponse clientToResponse(Client client);
}
