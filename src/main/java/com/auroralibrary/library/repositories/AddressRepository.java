package com.auroralibrary.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auroralibrary.library.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
  
}
