package com.api.nataly.poc1api.model.repositories;

import com.api.nataly.poc1api.model.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
