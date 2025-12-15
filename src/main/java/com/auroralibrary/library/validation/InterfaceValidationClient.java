package com.auroralibrary.library.validation;

import com.auroralibrary.library.dto.request.ClientCreateRequest;
import com.auroralibrary.library.model.Client;

public interface InterfaceValidationClient {

    void validateCreate(ClientCreateRequest client);

    void validateUpdate(Client client, Long id);
}
