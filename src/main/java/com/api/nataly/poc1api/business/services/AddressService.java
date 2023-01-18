package com.api.nataly.poc1api.business.services;

import com.api.nataly.poc1api.model.entities.Address;
import com.api.nataly.poc1api.presentation.dtos.AddressDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AddressService {

    public Address save(Address address);
    public Address update(Address address);
    public void delete(Long id);
    public Page<Address> findAllAddresses(Pageable pageable);
    public List<Address> findAddressesByFilter(Address filter);
    public Optional<Address> findById(Long id);

//    Address updateMainAddress(Long id);





}
