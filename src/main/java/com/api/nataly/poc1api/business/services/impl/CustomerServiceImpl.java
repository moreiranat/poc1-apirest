package com.api.nataly.poc1api.business.services.impl;

import com.api.nataly.poc1api.business.services.CustomerService;
import com.api.nataly.poc1api.model.entities.Customer;
import com.api.nataly.poc1api.model.repositories.CustomerRepository;
import com.api.nataly.poc1api.presentation.controllers.exceptions.MissingFieldException;
import com.api.nataly.poc1api.presentation.controllers.exceptions.ObjectAlreadyExistsException;
import com.api.nataly.poc1api.presentation.controllers.exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

        if(customerRepository.existsByEmail(customer.getEmail())) {
            throw new ObjectAlreadyExistsException("There is already a customer with e-mail " + customer.getEmail());
        }

        if(customerRepository.existsByDocumentNumber(customer.getDocumentNumber())) {
            throw new ObjectAlreadyExistsException("There is already a customer with CPF/CNPJ " + customer.getDocumentNumber());
        }

        if(customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
            throw new ObjectAlreadyExistsException("There is already a customer with phone number " + customer.getPhoneNumber());
        }

        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer update(Customer customer) {
        if(customer.getId() == null) {
            throw new MissingFieldException("id", "update");
        } else if(!customerRepository.existsById(customer.getId())) {
            throw new ObjectNotFoundException("customer", "id", customer.getId());
        }

        if(customerRepository.existsByEmail(customer.getEmail())) {
            Customer customerSaved = findByEmail(customer.getEmail()).get();
            if (!Objects.equals(customerSaved.getId(), customer.getId())) {
                throw new ObjectAlreadyExistsException("There is already a Customer with e-mail " + customer.getEmail());
            }
        }

        if(customerRepository.existsByDocumentNumber(customer.getDocumentNumber())) {
            Customer customerSaved = findByDocumentNumber(customer.getDocumentNumber()).get();
            if (!Objects.equals(customerSaved.getId(), customer.getId())) {
                throw new ObjectAlreadyExistsException("There is already a Customer with CPF/CNPJ " + customer.getDocumentNumber());
            }
        }

        if(customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
            Customer customerSaved = findByPhoneNumber(customer.getPhoneNumber()).get();
            if (!Objects.equals(customerSaved.getId(), customer.getId())) {
                throw new ObjectAlreadyExistsException("There is already a Customer with phone number " + customer.getPhoneNumber());
            }
        }

        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public void delete(Customer customer) {
        if(customer.getId() == null) {
            throw new MissingFieldException("id", "delete");
        } else if(!customerRepository.existsById(customer.getId())) {
            throw new ObjectNotFoundException("customer", "id", customer.getId());
        }

        customerRepository.delete(customer);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if(id == null) {
            throw new MissingFieldException("id", "delete");
        } else if(!customerRepository.existsById(id)) {
            throw new ObjectNotFoundException("customer", "id", id);
        }

        customerRepository.deleteById(id);
    }

    @Override
    public Page<Customer> find(Customer filter, Pageable pageable) {
        Example<Customer> example = Example.of(filter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return customerRepository.findAll(example, pageable);
    }

    @Override
    public Optional<Customer> findById(Long id) {

        return customerRepository.findById(id);
    }

    private Optional<Customer> findByName(String name) {
        if(name == null || name.isBlank()) {
            throw new MissingFieldException("name");
        }

        if (!customerRepository.existsByName(name)) {
            throw new ObjectNotFoundException("customer", "name", name);
        }

        return customerRepository.findByName(name);
    }

    private Optional<Customer> findByEmail(String email) {
        if(email == null || email.isBlank()) {
            throw new MissingFieldException("e-mail");
        }

        if (!customerRepository.existsByEmail(email)) {
            throw new ObjectNotFoundException("customer", "e-mail", email);
        }

        return customerRepository.findByEmail(email);
    }

    private Optional<Customer> findByDocumentNumber(String documentNumber) {
        if(documentNumber == null || documentNumber.isBlank()) {
            throw new MissingFieldException("CPF/CNPJ");
        }

        if (!customerRepository.existsByDocumentNumber(documentNumber)) {
            throw new ObjectNotFoundException("customer", "CPF/CNPJ", documentNumber);
        }

        return customerRepository.findByDocumentNumber(documentNumber);
    }

    private Optional<Customer> findByPhoneNumber(String phoneNumber) {
        if(phoneNumber == null || phoneNumber.isBlank()) {
            throw new MissingFieldException("phone number");
        }

        if (!customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new ObjectNotFoundException("customer", "phone number", phoneNumber);
        }

        return customerRepository.findByPhoneNumber(phoneNumber);
    }

}
