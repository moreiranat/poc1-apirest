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
            throw new MissingFieldException("name", "save");
        }

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new MissingFieldException("e-mail", "save");
        }

        if (customer.getDocumentNumber() == null || customer.getDocumentNumber().isBlank()) {
            throw new MissingFieldException("document number", "save");
        }

        if (customer.getPersonType() == null) {
            throw new MissingFieldException("person type", "save");
        }

        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            throw new MissingFieldException("phone number", "save");
        }

        if(customerRepository.existsByEmail(customer.getEmail())) {
            throw new ObjectAlreadyExistsException("There is already a customer with e-mail " + customer.getEmail());
        }

        if(customerRepository.existsByDocumentNumber(customer.getDocumentNumber())) {
            throw new ObjectAlreadyExistsException("There is already a customer with document number " + customer.getDocumentNumber());
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

        if(customer.getName() == null || customer.getName().isBlank()) {
            throw new MissingFieldException("name", "update");
        }

        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new MissingFieldException("e-mail", "update");
        }

        if (customer.getDocumentNumber() == null || customer.getDocumentNumber().isBlank()) {
            throw new MissingFieldException("document number", "update");
        }

        if (customer.getPersonType() == null) {
            throw new MissingFieldException("person type", "update");
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
                throw new ObjectAlreadyExistsException("There is already a Customer with document number " + customer.getDocumentNumber());
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

    private Optional<Customer> findByName(String name) {
        if(name == null) {
            throw new IllegalStateException("E-mail cannot be null");
        }

        if (!customerRepository.existsByName(name)) {
            throw new IllegalStateException("Customer with name " + name + " not found");
        }

        return customerRepository.findByName(name);
    }

    private Optional<Customer> findByEmail(String email) {
        if(email == null) {
            throw new IllegalStateException("E-mail cannot be null");
        }

        if (!customerRepository.existsByEmail(email)) {
            throw new IllegalStateException("Customer with e-mail " + email + " not found");
        }

        return customerRepository.findByEmail(email);
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

    private Optional<Customer> findByPhoneNumber(String phoneNumber) {
        if(phoneNumber == null) {
            throw new IllegalStateException("Phone number cannot be null");
        }

        if (!customerRepository.existsByPhoneNumber(phoneNumber)) {
            throw new IllegalStateException("Customer with phone number " + phoneNumber + " not found");
        }

        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    @Transactional
    public void delete(Customer customer) {
        if(customer.getId() == null) {
            throw new MissingFieldException("id", "delete");
        } else if (!customerRepository.existsById(customer.getId())) {
            throw new ObjectNotFoundException("customer", "id", customer.getId());
        }

        customerRepository.delete(customer);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if(id == null) {
            throw new MissingFieldException("id", "delete");
        } else if (!customerRepository.existsById(id)) {
            throw new ObjectNotFoundException("customer", "id", id);
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

}
