package com.auroralibrary.library.validation;

import com.auroralibrary.library.dto.request.ClientUpdateRequest;
import com.auroralibrary.library.dto.response.ClientResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

@Component
public class ValidateUpdateClient {

    public static ClientUpdateRequest validateFieldsUpdate(ClientResponse response, ClientUpdateRequest update) {
        @NotBlank(message = "Nome inválido")
        String name = (update.name() == null || update.name().isEmpty())
                ? response.name()
                : update.name();

        @NotBlank(message = "Telefone inválido")
        String phone = (update.phone() == null || update.phone().isEmpty())
                ? response.phone()
                : update.phone();

        @NotBlank(message = "Email inválido")
        @Email(message = "Email inválido")
        String email = (update.email() == null || update.email().isEmpty())
                ? response.email()
                : update.email();

        return new ClientUpdateRequest(name, phone, email, update.address());
    }
}
