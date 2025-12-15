package com.auroralibrary.library.repositories;

import com.auroralibrary.library.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/* É uma Interface onde realizará operações com o BD
  Nesse contexto é o CRUD, pois recebe métodos do extends JpaRepository */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
}
