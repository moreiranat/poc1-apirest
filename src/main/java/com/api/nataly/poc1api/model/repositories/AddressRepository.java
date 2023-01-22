package com.api.nataly.poc1api.model.repositories;

import com.api.nataly.poc1api.model.entities.Address;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    int findByCustomerId(Long customerId);
}
