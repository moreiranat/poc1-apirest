package com.api.nataly.poc1api.business.services;

import com.api.nataly.poc1api.model.entities.Address;
import com.api.nataly.poc1api.presentation.dtos.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressConverterService {

    public Address dtoToAddress(AddressDTO dto);
    public AddressDTO addressToDTO(Address entity);
    public List<AddressDTO> addressToDTOList(List<Address> entities);
    List<Address> dtoToAddressList(List<AddressDTO> dtos);
}
