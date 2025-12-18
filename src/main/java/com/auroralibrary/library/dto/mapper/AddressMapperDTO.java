package com.auroralibrary.library.dto.mapper;

import com.auroralibrary.library.dto.request.AddressCreateRequest;
import com.auroralibrary.library.dto.request.AddressUpdateRequest;
import com.auroralibrary.library.dto.response.AddressResponse;
import com.auroralibrary.library.dto.response.CepResponse;
import com.auroralibrary.library.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapperDTO {

  @Mapping(target = "cep", source = "request.cep")
  @Mapping(target = "number", source = "request.number")
  @Mapping(target = "state", source = "cepResponse.state")
  @Mapping(target = "city", source = "cepResponse.city")
  @Mapping(target = "neighborhood", source = "cepResponse.neighborhood")
  @Mapping(target = "street", source = "cepResponse.street")
  Address mapCepResponseToAddressCreate(CepResponse cepResponse, AddressCreateRequest request);

  @Mapping(target = "idAddress", ignore = true)
  @Mapping(target = "number", ignore = true)
  @Mapping(target = "cep", source = "updateRequest.cep")
  void updateAddressFromCepResponse(@MappingTarget Address address, CepResponse cepResponse, AddressUpdateRequest updateRequest);

  Address addressDtoToAddress(AddressCreateRequest addressCreateRequest);

  @Mapping(target = "id", source = "idAddress")
  AddressResponse addressToResponse(Address address);

  @Mapping(target = "idAddress", source = "id")
  Address responseToAddress(AddressResponse response);

}
