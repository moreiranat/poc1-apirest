package com.api.nataly.poc1api.business.services.impl;

import com.api.nataly.poc1api.business.services.AddressConverterService;
import com.api.nataly.poc1api.model.entities.Address;
import com.api.nataly.poc1api.presentation.dtos.AddressDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressConverterServiceImpl implements AddressConverterService {

    private static final String MESSAGE = "Could not convert, because object is null";

    @Override
    public Address dtoToAddress(AddressDTO dto) {
        if (dto != null) {
            Address entity = new Address();

            entity.setId(dto.getId());
            entity.setCep(dto.getCep());
            entity.setLogradouro(dto.getLogradouro());
            entity.setComplemento(dto.getComplemento());
            entity.setBairro(dto.getBairro());
            entity.setLocalidade(dto.getLocalidade());
            entity.setUf(dto.getUf());
            entity.setIsMainAddress(dto.getIsMainAddress());
            //entity.setCustomer(dto.getCustomerId().toString());

            return entity;
        }
        throw new IllegalArgumentException(MESSAGE);
    }

    @Override
    public AddressDTO addressToDTO(Address entity) {

        if (entity != null) {
            AddressDTO dto = new AddressDTO();

            dto.setId(entity.getId());
            dto.setCep(entity.getCep());
            dto.setLogradouro(entity.getLogradouro());
            dto.setComplemento(entity.getComplemento());
            dto.setBairro(entity.getBairro());
            dto.setLocalidade(entity.getLocalidade());
            dto.setUf(entity.getUf());
            dto.setIsMainAddress(entity.getIsMainAddress());
            dto.setCustomerId(entity.getCustomer().getId());

            return dto;
        }
        throw new IllegalArgumentException(MESSAGE);
    }

    @Override
    public List<AddressDTO> addressToDTOList(List<Address> entities) {
        if(entities != null) {
            List<AddressDTO> dtos = new ArrayList<>();

            if(!entities.isEmpty()) {
                for (Address address: entities) {
                    AddressDTO dto = addressToDTO(address);
                    dtos.add(dto);
                }
            }

            return dtos;
        }
        throw new IllegalArgumentException(MESSAGE);
    }

    @Override
    public List<Address> dtoToAddressList(List<AddressDTO> dtos) {
        if(dtos != null) {
            List<Address> entities = new ArrayList<>();

            if(!dtos.isEmpty()) {
                for (AddressDTO dto : dtos) {
                    Address entity = dtoToAddress(dto);
                    entities.add(entity);
                }
            }

            return entities;
        }
        throw new IllegalArgumentException(MESSAGE);
    }

}
