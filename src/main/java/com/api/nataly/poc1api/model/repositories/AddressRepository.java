package com.api.nataly.poc1api.model.repositories;

import com.api.nataly.poc1api.model.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

//    int findByCustomerId(Long customerId);

    Optional<Address> findByCustomerIdAndMainAddress(Long customerId, boolean b);

//    boolean existsByCustomerId(Long customerId);
}
