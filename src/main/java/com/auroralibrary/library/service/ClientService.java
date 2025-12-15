package com.auroralibrary.library.service;

import java.util.List;
import com.auroralibrary.library.dto.mapper.ClientMapperDTO;
import com.auroralibrary.library.model.Client;
import com.auroralibrary.library.repositories.AddressRepository;
import com.auroralibrary.library.repositories.specification.ClientSpecification;
import com.auroralibrary.library.validation.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.auroralibrary.library.dto.mapper.AddressMapper;
import com.auroralibrary.library.dto.request.ClientCreateRequest;
import com.auroralibrary.library.dto.request.ClientUpdateRequest;
import com.auroralibrary.library.dto.response.AddressResponse;
import com.auroralibrary.library.dto.response.ClientResponse;
import com.auroralibrary.library.exception.DatabaseException;
import com.auroralibrary.library.exception.ResourceNotFoundException;
import com.auroralibrary.library.exception.ValidationException;
import com.auroralibrary.library.model.Address;
import com.auroralibrary.library.repositories.ClientRepository;
import com.auroralibrary.library.dto.mapper.ClientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/* É uma classe Serviço,
que define como o processamento deve ser realizado (CRUD), 
contendo a lógica para a realização do CRUD na interface Repository */
@Slf4j
@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private List<InterfaceValidationClient> validations;

    @Autowired
    private ClientMapperDTO clientMapperDTO;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    /* Métodos para o processamento dos Dados */
    @Transactional
    public ClientResponse create(ClientCreateRequest clientRequest) {
        try {
            AddressResponse addressCreated = addressService.create(clientRequest.address());
            Address address = addressRepository.getReferenceById(addressCreated.id());

            // Valida Campos e formata ele para o BD.
            validations.forEach(v -> v.validateCreate(clientRequest));

            // Transfere os dados da DTO para a Entidade JPA
            Client client = clientMapperDTO.clientToCreate(clientRequest);
            client.atribuiValoresCreate(client.getCpf(), client.getPhone(), address);

            Client savedClient = clientRepository.save(client);
            log.info("Cliente criado com sucesso - ID: {}", savedClient.getIdClient());

            return clientMapperDTO.clientToResponse(savedClient);

        } catch (DataIntegrityViolationException e) {
            log.error("Error ao registrar Cliente: {}", e.getMessage());
            throw new DatabaseException("Erro ao registrar Cliente, dados duplicados ou inválidos");
        } catch (ValidationException e) {
            log.error("Erro na validação do CPF: {}", e.getMessage());
            throw e;
        }
    }

    public Page<ClientResponse> findAll(Pageable pageable) {
        Page<Client> clients = clientRepository.findAll(pageable);

        if (clients.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum Cliente encontrado");
        }

        return clients.map(ClientMapper::toResponse);
    }

    public ClientResponse findById(Long id_client) {
        try {
            Client client = clientRepository.findById(id_client)
                .orElseThrow(() -> {
                    log.error("Cliente com ID {} não encontrado", id_client);
                    return new ResourceNotFoundException("Cliente não encontrado");
                });

            log.info("Cliente encontrado com sucesso: ID {}", id_client);
            return clientMapperDTO.clientToResponse(client);

        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao buscar Cliente, ID inválido: {}", e.getMessage());
            throw new DatabaseException("ID inválido");
        }
    }

    public Page<ClientResponse> findByFilters(String name, String cpf, Pageable pageable) {
        Specification<Client> clientSpecification = Specification
                .where(ClientSpecification.hasName(name))
                .and(ClientSpecification.hasCpf(cpf));

        Page<Client> clients = clientRepository.findAll(clientSpecification, pageable);

        return clients.map(ClientMapper::toResponse);
    }
    
    @Transactional
    public ClientResponse update(ClientUpdateRequest clientUpdate, Long id_client) {
        try {
            ClientResponse readClient = findById(id_client);

            // Valida Campos e formata ele para o BD.
            String phoneValid = StringFormatter.removeSpecialCharacters(clientUpdate.phone());
            ClientUpdateRequest validatedUpdate = ValidateUpdateClient.validateFieldsUpdate(readClient, clientUpdate);

            AddressResponse updatedAddress = null;
            if (clientUpdate.address().isPresent()) {
                updatedAddress = addressService.update(clientUpdate.address().get(),
                        readClient.address().id());
            }

            // Transfere os dados da DTO para a Entidade JPA
            Address address = updatedAddress != null ?
                    AddressMapper.toResponseAddress(updatedAddress) : AddressMapper.toResponseAddress(readClient.address());
            Client client = ClientMapper.clientUpdateMapper(readClient, validatedUpdate);
            client.atribuiValoresUpdate(phoneValid, address);

            // Salva as alterações no banco de dados
            Client updatedClient = clientRepository.save(client);
            return clientMapperDTO.clientToResponse(updatedClient);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro ao atualizar Cliente: dados duplicados ou inválidos");
        }
    }

    public void delete(Long id) {
        try {
            findById(id);
            clientRepository.deleteById(id);
            log.info("Cliente excluído com sucesso: ID {}", id);
        } catch (DataIntegrityViolationException e) {
            log.error("Erro ao excluir Cliente");
            throw new DatabaseException("ID inválido");
        }
    }
}
