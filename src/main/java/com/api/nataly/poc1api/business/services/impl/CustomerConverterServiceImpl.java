package com.api.nataly.poc1api.business.services.impl;

import com.api.nataly.poc1api.business.services.CustomerConverterService;
import com.api.nataly.poc1api.model.entities.Customer;
import com.api.nataly.poc1api.presentation.dtos.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerConverterServiceImpl implements CustomerConverterService {

    private static final String MESSAGE = "Could not convert, because object is null";

    @Override
    public Customer dtoToCustomer(CustomerDTO dto) {
        if (dto != null) {
            Customer entity = new Customer();

            entity.setId(dto.getId());
            entity.setName(dto.getName());
            entity.setEmail(dto.getEmail());
            entity.setDocumentNumber(dto.getDocumentNumber());
            entity.setPersonType(dto.getPersonType());
            entity.setPhoneNumber(dto.getPhoneNumber());
            entity.setAddresses(dto.getAddresses());

            return entity;
        }
        throw new IllegalArgumentException(MESSAGE);
    }

    @Override
    public CustomerDTO customerToDTO(Customer entity) {
        if (entity != null) {
            CustomerDTO dto = new CustomerDTO();

            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setEmail(entity.getEmail());
            dto.setPersonType(entity.getPersonType());
            dto.setAddresses(entity.getAddresses());

            return dto;
        }
        throw new IllegalArgumentException(MESSAGE);
    }

    @Override
    public List<CustomerDTO> customerToDTOList(List<Customer> entities) {
        if(entities != null) {
            List<CustomerDTO> dtos = new ArrayList<>();

            if(!entities.isEmpty()) {
                for (Customer customer: entities) {
                    CustomerDTO dto = customerToDTO(customer);
                    dtos.add(dto);
                }
            }

            return dtos;
        }
        throw new IllegalArgumentException(MESSAGE);
    }

    @Override
    public List<Customer> dtoToCustomerList(List<CustomerDTO> dtos) {
        if(dtos != null) {
            List<Customer> entities = new ArrayList<>();

            if(!dtos.isEmpty()) {
                for (CustomerDTO dto : dtos) {
                   Customer entity = dtoToCustomer(dto);
                    entities.add(entity);
                }
            }

            return entities;
        }
        throw new IllegalArgumentException(MESSAGE);
    }
}
