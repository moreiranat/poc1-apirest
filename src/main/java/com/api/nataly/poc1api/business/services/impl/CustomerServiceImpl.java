package com.api.nataly.poc1api.business.services.impl;

import com.api.nataly.poc1api.business.services.CustomerService;
import com.api.nataly.poc1api.model.entities.Customer;
import com.api.nataly.poc1api.model.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        if(customer.getId() != null) {
            throw new IllegalStateException("Customer is already in the database");
        }

        if (customer.getName() == null || customer.getName().isBlank()) {
            throw new IllegalStateException("Could not save the customer because the name field is blank!");
        }

        if(customerRepository.existsByDocumentNumber(customer.getDocumentNumber())) {
            throw new IllegalStateException("There is already a customer with document number " + customer.getDocumentNumber());
        }

        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        if(customer.getId() == null) {
            throw new IllegalStateException("Customer id cannot be null");
        } else if(!customerRepository.existsById(customer.getId())) {
            throw new IllegalStateException("Could not find a Customer with id " + customer.getId());
        }

        if(customer.getName() == null || customer.getName().isBlank()) {
            throw new IllegalStateException("Could not update the Customer because the name field is blank!");
        }

        if(customerRepository.existsByDocumentNumber(customer.getDocumentNumber())) {
            Customer customerSaved = findByDocumentNumber(customer.getDocumentNumber()).get();
            if (!Objects.equals(customerSaved.getId(), customer.getId())) {
                throw new IllegalStateException("There is already a Customer with document number " + customer.getDocumentNumber());
            }
        }
        return customerRepository.save(customer);
    }

    private Optional<Customer> findByDocumentNumber(String documentNumber) {
        if(documentNumber == null) {
            throw new IllegalStateException("Document number cannot be null");
        }

        if (!customerRepository.existsByDocumentNumber(documentNumber)) {
            throw new IllegalStateException("Customer with document number " + documentNumber + " not found");
        }

        return customerRepository.findByDocumentNumber(documentNumber);
    }

    @Override
    @Transactional
    public void delete(Customer customer) {
        if (customer.getId() == null) {
            throw new IllegalStateException("Customer id cannot be null");
        } else if (!customerRepository.existsById(customer.getId())) {
            throw new IllegalStateException("Customer with id " + + customer.getId() + " not found");
        }

        customerRepository.delete(customer);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalStateException("Id cannot be null");
        } else if (!customerRepository.existsById(id)) {
            throw new IllegalStateException("Customer with id " + id + " not found");
        }

        customerRepository.deleteById(id);
    }

    @Override
    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public List<Customer> findCustomersByFilter(Customer filter) {
        Example<Customer> example = Example.of(filter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return customerRepository.findAll(example);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return customerRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return customerRepository.existsByPhoneNumber(phoneNumber);
    }
}
