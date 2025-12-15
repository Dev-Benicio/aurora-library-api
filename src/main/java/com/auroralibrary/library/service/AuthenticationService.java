package com.auroralibrary.library.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.auroralibrary.library.repositories.AdministratorRepository;

@Service
// Classe Serviço de autenticação
public class AuthenticationService implements UserDetailsService {
  
  @Autowired
  private AdministratorRepository adminRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return Optional.ofNullable(adminRepository.findByLogin(username))
        .orElseThrow(() -> new UsernameNotFoundException(
            "Registro Inválido!"));
  }
}
