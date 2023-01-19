package com.api.nataly.poc1api.presentation.controllers;

import com.api.nataly.poc1api.business.services.AddressConverterService;
import com.api.nataly.poc1api.business.services.AddressService;
import com.api.nataly.poc1api.business.services.CustomerService;
import com.api.nataly.poc1api.model.entities.Address;
import com.api.nataly.poc1api.presentation.dtos.AddressDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService service;
    private final CustomerService customerService; //ver se vai precisar (tirar do construtor também, caso não precise)
    private final AddressConverterService converter;

    public AddressController(AddressService service, CustomerService customerService, AddressConverterService converter) {
        this.service = service;
        this.customerService = customerService;
        this.converter = converter;
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody AddressDTO dto) {

        try {
            Address entity = converter.dtoToAddress(dto);
            entity = service.save(entity);
            dto = converter.addressToDTO(entity);

            return new ResponseEntity(dto, HttpStatus.CREATED); //status 201 Created

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //status 400 Bad Request
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody AddressDTO dto) {

        try {
            dto.setId(id);
            Address entity = converter.dtoToAddress(dto);
            entity = service.update(entity);
            dto = converter.addressToDTO(entity);

            return ResponseEntity.ok(dto); //status 200 Ok

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //status 400 Bad Request
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);

            return new ResponseEntity(HttpStatus.NO_CONTENT); //status 204 No Content

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //status 400 Bad Request
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Address>> findAllAddresses(@PageableDefault(page = 0, size = 10, sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllAddresses(pageable)); //status 200 Ok
    }

    @GetMapping
    public ResponseEntity findAddressesByFilter(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "cep", required = false) String cep,
            @RequestParam(value = "logradouro", required = false) String logradouro,
            @RequestParam(value = "complemento", required = false) String complemento,
            @RequestParam(value = "bairro", required = false) String bairro,
            @RequestParam(value = "localidade", required = false) String localidade,
            @RequestParam(value = "uf", required = false) String uf
            //@RequestParam(value = "customerId") Long customerId
    ) {
        try {
            Address filter = new Address();
            filter.setId(id);
            filter.setCep(cep);
            filter.setLogradouro(logradouro);
            filter.setComplemento(complemento);
            filter.setBairro(bairro);
            filter.setLocalidade(localidade);
            filter.setUf(uf);

//            Optional<Customer> customer = customerService.findById(customerId);
//
//            if(customer == null) {
//                throw new ObjectNotFoundException("customer", "customerId", customerId);
//            }
//
//            filter.setCustomer(customer.get()); //ver customer.get()

            List<Address> entities = service.findAddressesByFilter(filter);
            List<AddressDTO> dtos = converter.addressToDTOList(entities);

            return ResponseEntity.ok(dtos); //status 200 Ok

        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //status 400 Bad Request
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {

        Optional<Address> addressOptional = service.findById(id);

        if(!addressOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Address not found."); //status 404 Not Found
        }
        return ResponseEntity.status(HttpStatus.OK).body(addressOptional.get()); //status 200 Ok
    }
}
