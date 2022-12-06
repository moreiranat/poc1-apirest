package com.api.nataly.poc1api.model.repositories;

import com.api.nataly.poc1api.model.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
