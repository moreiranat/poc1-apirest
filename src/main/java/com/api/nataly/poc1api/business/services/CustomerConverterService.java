package com.api.nataly.poc1api.business.services;

import com.api.nataly.poc1api.model.entities.Customer;
import com.api.nataly.poc1api.presentation.dtos.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerConverterService {

    public Customer dtoToCustomer(CustomerDTO dto);
    public CustomerDTO customerToDTO(Customer entity);
    public List<CustomerDTO> customerToDTOList(List<Customer> entities);
    List<Customer> dtoToCustomerList(List<CustomerDTO> dtos);

}
