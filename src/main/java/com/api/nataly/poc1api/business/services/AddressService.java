package com.api.nataly.poc1api.business.services;

import com.api.nataly.poc1api.model.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AddressService {

    public Address save(Address address);
    public Address update(Address address);
    public void delete(Long id);
    public Page<Address> find(Address filter, Pageable pageable);
    public Optional<Address> findById(Long id);

}
