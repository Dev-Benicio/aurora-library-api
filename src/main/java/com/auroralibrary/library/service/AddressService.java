package com.auroralibrary.library.service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import com.auroralibrary.library.dto.mapper.AddressMapperDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.auroralibrary.library.client.CepClient;
import com.auroralibrary.library.dto.request.AddressCreateRequest;
import com.auroralibrary.library.dto.request.AddressUpdateRequest;
import com.auroralibrary.library.dto.response.AddressResponse;
import com.auroralibrary.library.dto.response.CepResponse;
import com.auroralibrary.library.exception.DatabaseException;
import com.auroralibrary.library.exception.ValidationCepException;
import com.auroralibrary.library.exception.ValidationException;
import com.auroralibrary.library.model.Address;
import com.auroralibrary.library.repositories.AddressRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AddressService {
  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private CepClient cepClient;

  @Autowired
  private AddressMapperDTO addressMapperDTO;

  @Transactional
  public AddressResponse create(AddressCreateRequest addressRequest) {
    try {
      CepResponse cepResponse = cepClient.validateAddress(addressRequest.cep()).get(10, TimeUnit.SECONDS);

      // Transferencia de dados
      Address mappedAddress = addressMapperDTO.mapCepResponseToAddressCreate(cepResponse, addressRequest);
      mappedAddress.atribuiValoresCreate(mappedAddress.getCep());

      Address savedAddress = addressRepository.save(mappedAddress);

      log.info("Endereço criado com sucesso, Body: {}", savedAddress);
      return addressMapperDTO.addressToResponse(savedAddress);

    } catch (DataIntegrityViolationException e) {
        log.error("Error ao registrar Endereço: {}", e.getMessage());
        throw new DatabaseException("Erro ao registrar Endereço, dados duplicados ou inválidos");
    } catch (TimeoutException e) {
        throw new ValidationCepException("Tempo excedido na validação do CEP");
    } catch (InterruptedException | ExecutionException e) {
        throw new ValidationCepException("Erro na validação do CEP");
    }
  }

  @Transactional
  public AddressResponse update(AddressUpdateRequest addressUpdateRequest, Long id_address) {
    try {
      Address address = addressRepository.getReferenceById(id_address);

      if (addressUpdateRequest.cep() != null && !addressUpdateRequest.cep().isEmpty()) {
        CepResponse cepResponse = cepClient.validateAddress(addressUpdateRequest.cep()).get(10, TimeUnit.SECONDS);

        addressMapperDTO.updateAddressFromCepResponse(address, cepResponse, addressUpdateRequest);
        address.atribuiValoresUpdate(address);
      }

      Optional.ofNullable(addressUpdateRequest.number())
              .ifPresent(address::setNumber);

      Address savedAddress = addressRepository.save(address);
      log.info("Endereço atualizado com sucesso: ID {}", id_address);

      return addressMapperDTO.addressToResponse(savedAddress);

    } catch (DataIntegrityViolationException e) {
      log.error("Erro ao atualizar endereço: {}", e.getMessage());
      throw new DatabaseException("Erro ao atualizar endereço: dados duplicados ou inválidos");
    } catch (TimeoutException e) {
      throw new ValidationException("Tempo excedido na validação do CEP");
    } catch (InterruptedException | ExecutionException e) {
      throw new ValidationException("Erro na validação do CEP");
    }
  }
}
