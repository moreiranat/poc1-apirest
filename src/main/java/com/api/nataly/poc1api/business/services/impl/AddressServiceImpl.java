package com.api.nataly.poc1api.business.services.impl;

import com.api.nataly.poc1api.business.services.AddressConverterService;
import com.api.nataly.poc1api.business.services.AddressService;
import com.api.nataly.poc1api.business.services.CustomerService;
import com.api.nataly.poc1api.model.entities.Address;
import com.api.nataly.poc1api.model.entities.Customer;
import com.api.nataly.poc1api.model.repositories.AddressRepository;
import com.api.nataly.poc1api.presentation.controllers.exceptions.*;
import com.api.nataly.poc1api.presentation.dtos.AddressDTO;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final AddressConverterService addressConverterService;

    private final CustomerService customerService;

    public AddressServiceImpl(AddressRepository addressRepository, AddressConverterService addressConverterService, CustomerService customerService) {
        this.addressRepository = addressRepository;
        this.addressConverterService = addressConverterService;
        this.customerService = customerService;
    }

    @Override
    @Transactional
    public Address save(AddressDTO addressDTO) {

        searchCep(addressDTO);

        Customer customer = customerService.findById(addressDTO.getCustomerId());

        Address address = new Address();
        address.setIsMainAddress(customer.getAdresses().isEmpty());

        BeanUtils.copyProperties(addressDTO, Address.class);

        countMaximumAddressLimit(customer);

        return addressRepository.save(address);

    }

    private void countMaximumAddressLimit(Customer customer) {
        if (customer.getAdresses().size() >= 5) {
            throw new MaximumAddressLimitExceededException(customer.getId());
        }
    }

    //Consumindo API publica externa
    private Address searchCep(AddressDTO addressDTO) {

        try {
            URL url = new URL("https://viacep.com.br/ws/" + addressDTO.getCep() + "/json/");
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String cep = "";
            StringBuilder jsonCep = new StringBuilder();
            while ((cep = br.readLine()) != null) {
                jsonCep.append(cep);
            }

            AddressDTO addressAux = new Gson().fromJson(jsonCep.toString(), AddressDTO.class);
            addressDTO.setCep(addressAux.getCep());
            addressDTO.setLogradouro(addressAux.getLogradouro());
            addressDTO.setBairro(addressAux.getBairro());
            addressDTO.setLocalidade(addressAux.getLocalidade());
            addressDTO.setUf(addressAux.getUf());

        } catch (Exception e) {
            throw new ZipCodeInvalidException(addressDTO.getCep());
        }

        return addressConverterService.dtoToAddress(addressDTO);
    }

    @Override
    @Transactional
    public Address update(Long id, AddressDTO addressDTO) {
       Address address = findById(id);

        if(address.getId() == null) {
            throw new MissingFieldException("id", "update");
        } else if(!addressRepository.existsById(address.getId())) {
            throw new ObjectNotFoundException("address", "id", address.getId());
        }

        address.setCep(addressDTO.getCep());
        address.setLogradouro(addressDTO.getLogradouro());
        address.setComplemento(addressDTO.getComplemento());
        address.setBairro(addressDTO.getBairro());
        address.setLocalidade(addressDTO.getLocalidade());
        address.setUf(addressDTO.getUf());

        address = searchCep(addressDTO);

        BeanUtils.copyProperties(addressDTO, address);

        return addressRepository.save(address);
    }

    @Override
    public Address updateMainAddress(Long id) {
        Address address = findById(id);

        if(address.getId() == null) {
            throw new MissingFieldException("id", "update");
        } else if(!addressRepository.existsById(address.getId())) {
            throw new ObjectNotFoundException("address", "id", address.getId());
        }

        address.getCustomer().getAdresses()
                .forEach(a -> {
                    a.setIsMainAddress(false);
                    addressRepository.save(a);
                });

        address.setIsMainAddress(true);
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Address address = findById(id);

        if(id == null) {
            throw new MissingFieldException("id", "delete");
        } else if(!addressRepository.existsById(id)) {
            throw new ObjectNotFoundException("address", "id", id);
        }

        if(address.getIsMainAddress().equals(Boolean.TRUE)) {
            throw  new DeleteMainAddressException();

        }

        addressRepository.deleteById(id);


    }

    @Override
    public Page<Address> findAllAddresses(Pageable pageable) {
        return null;
    }

    @Override
    public List<Address> findAddressesByFilter(Address filter) {
        return null;
    }

    @Override
    public Address findById(Long id) {

        return addressRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("address", "id", id));


    }
}
