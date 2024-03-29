package com.api.nataly.poc1api.business.services;

import com.api.nataly.poc1api.model.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface CustomerService {

    public Customer save(Customer customer);
    public Customer update(Customer customer);
    public void delete(Customer customer);
    public void deleteById(Long id); //deleta o Customer passando o id dele
    public Page<Customer> find(Customer filter, Pageable pageable);
    public Optional<Customer> findById(Long id);

}
