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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

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
    public Address save(Address address) {

        //Regra de negocio
        Optional<Customer> optionalCustomer = customerService.findById(address.getCustomer().getId());
        Customer customer = optionalCustomer.get();
        if(optionalCustomer.isPresent()) {
            limitMaximumNumberOfRegisteredAddresses(customer);
            if(Boolean.TRUE.equals(address.getMainAddress())){
                removerMainAddress(address.getCustomer().getId());
            }
        }

        //Para garantir que o primeiro endereço seja o principal
        if(customer.getAddresses().isEmpty() && Boolean.TRUE.equals(!address.getMainAddress())) {
            address.setMainAddress(true);
        }
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public Address update(Address address) {
        if(address.getId() == null) {
            throw new MissingFieldException("id", "update");
        } else if(!addressRepository.existsById(address.getId())) {
            throw new ObjectNotFoundException("address", "id", address.getId());
        }
        if(Boolean.TRUE.equals(address.getMainAddress())){
            removerMainAddress(address.getCustomer().getId());
        }

        return addressRepository.save(address);
    }

    private void removerMainAddress(Long customerId) {
        Optional<Address> optionalAddress = addressRepository.findByCustomerIdAndMainAddress(customerId, true);

        if(optionalAddress.isPresent()){

            Address address = optionalAddress.get();
            address.setMainAddress(false);
            addressRepository.save(address);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var address = new Address();

        if(id == null) {
            throw new MissingFieldException("id", "delete");
        } else if(!addressRepository.existsById(id)) {
            throw new ObjectNotFoundException("address", "id", id);
        }

        if(address.getMainAddress().equals(Boolean.TRUE)) {
            throw new MainAddressCannotBeDeletedException();
        }
        addressRepository.deleteById(id);
    }

//    @Override
//    public Page<Address> findAllAddresses(Pageable pageable) {
//        return addressRepository.findAll(pageable);
//    }

    @Override
    public Page<Address> find(Address filter, Pageable pageable) {
        Example<Address> example = Example.of(filter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return addressRepository.findAll(example, pageable);
    }

    @Override
    public Optional<Address> findById(Long id) {

        return addressRepository.findById(id);
    }

    private void limitMaximumNumberOfRegisteredAddresses(Customer customer) {

        int totalNumberOfAddresses = customer.getAddresses().size();

        int addressLimit = 5;

        if (totalNumberOfAddresses >= addressLimit) {
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
}
