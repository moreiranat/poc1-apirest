package com.api.nataly.poc1api.model.repositories;

import com.api.nataly.poc1api.model.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    public Optional<Customer> findByDocumentNumber(String documentNumber);
    public boolean existsByDocumentNumber(String documentNumber);
    public boolean existsByPhoneNumber(String phoneNumber);
}
