package com.api.nataly.poc1api.model.repositories;

import com.api.nataly.poc1api.model.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Optional<Customer> findByName(String name);
    public Optional<Customer> findByEmail(String email);
    public Optional<Customer> findByDocumentNumber(String documentNumber);
    public Optional<Customer> findByPhoneNumber(String phoneNumber);
    public boolean existsByName(String name);
    public boolean existsByEmail(String email);
    public boolean existsByDocumentNumber(String documentNumber);
    public boolean existsByPhoneNumber(String phoneNumber);

}
