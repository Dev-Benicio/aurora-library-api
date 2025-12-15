package com.auroralibrary.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import com.auroralibrary.library.model.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

  UserDetails findByLogin(String login);
}
