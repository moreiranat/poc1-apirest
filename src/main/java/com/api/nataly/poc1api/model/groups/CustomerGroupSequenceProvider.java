package com.api.nataly.poc1api.model.groups;

import com.api.nataly.poc1api.presentation.dtos.CustomerDTO;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class CustomerGroupSequenceProvider implements DefaultGroupSequenceProvider<CustomerDTO> {

    @Override
    public List<Class<?>> getValidationGroups(CustomerDTO customerDTO) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(CustomerDTO.class);

        if(isPersonSelected(customerDTO)) {
            groups.add(customerDTO.getPersonType().getGroup());
        }

        return groups;
    }

    protected boolean isPersonSelected(CustomerDTO customerDTO){
        return customerDTO != null && customerDTO.getPersonType() != null;
    }
}
