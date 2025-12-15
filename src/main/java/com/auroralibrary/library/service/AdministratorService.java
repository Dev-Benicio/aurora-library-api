package com.auroralibrary.library.service;

import com.auroralibrary.library.dto.response.AdministratorResponse;
import com.auroralibrary.library.repositories.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    public AdministratorResponse getAdministrator(Long id) {
        var administrator = administratorRepository.findById(id).orElseThrow();

        administrator.atribuindoValores(administrator.getProfilePicture());

        return new AdministratorResponse(
                administrator.getIdAdmin(),
                administrator.getName(),
                administrator.getLogin(),
                administrator.getProfilePicture()
        );
    }
}
