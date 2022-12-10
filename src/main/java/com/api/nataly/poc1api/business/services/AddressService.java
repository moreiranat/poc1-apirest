package com.api.nataly.poc1api.business.services;

import com.api.nataly.poc1api.model.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AddressService {

    public Address save(Address address);
    public Address update(Address address);
    public void delete(Address address);
    public void deleteById(Long id); //deleta o Address passando o id dele
    public Page<Address> findAllCustomers(Pageable pageable);
    public List<Address> findCustomersByFilter(Address filter);
    public Optional<Address> findById(Long id);
    public boolean existsById(Long id);
}
