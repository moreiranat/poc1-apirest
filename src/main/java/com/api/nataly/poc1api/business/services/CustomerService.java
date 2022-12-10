package com.api.nataly.poc1api.business.services;

import com.api.nataly.poc1api.model.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerService {

    public Customer save(Customer customer);
    public Customer update(Customer customer);
    public void delete(Customer customer);
    public void deleteById(Long id); //deleta o Customer passando o id dele
    public Page<Customer> findAllCustomers(Pageable pageable);
    public List<Customer> findCustomersByFilter(Customer filter);
    public Optional<Customer> findById(Long id);
    public boolean existsByDocumentNumber(String documentNumber);
    public boolean existsById(Long id);
    public boolean existsByPhoneNumber(String phoneNumber);

}
