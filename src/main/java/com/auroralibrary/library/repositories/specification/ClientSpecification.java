package com.auroralibrary.library.repositories.specification;

import com.auroralibrary.library.model.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    public static Specification<Client> hasName(String name){
        return (root, query, criteriaBuilder)
                -> name != null ? criteriaBuilder.like(root.get("name"), "%" + name + "%") : null;
    }

    public static Specification<Client> hasCpf(String cpf){
        return (root, query, criteriaBuilder)
                -> cpf != null ? criteriaBuilder.equal(root.get("cpf"), cpf) : null;
    }
}
