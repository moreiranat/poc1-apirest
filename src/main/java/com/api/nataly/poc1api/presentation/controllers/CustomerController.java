package com.api.nataly.poc1api.presentation.controllers;

import com.api.nataly.poc1api.business.services.CustomerConverterService;
import com.api.nataly.poc1api.business.services.CustomerService;
import com.api.nataly.poc1api.model.entities.Customer;
import com.api.nataly.poc1api.presentation.dtos.CustomerDTO;
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
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerConverterService customerConverterService;

    public CustomerController(CustomerService customerService, CustomerConverterService customerConverterService) {
        this.customerService = customerService;
        this.customerConverterService = customerConverterService;
    }

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody CustomerDTO dto) {

        try {
            Customer entity = customerConverterService.dtoToCustomer(dto);
            entity = customerService.save(entity);
            dto = customerConverterService.customerToDTO(entity);

            return new ResponseEntity(dto, HttpStatus.CREATED); //status 201 Created

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //status 400 Bad Request
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody CustomerDTO dto) {

        try {
            dto.setId(id);
            Customer entity = customerConverterService.dtoToCustomer(dto);
            entity = customerService.update(entity);
            dto = customerConverterService.customerToDTO(entity);

            return ResponseEntity.ok(dto); //status 200 Ok

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //status 400 Bad Request
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            customerService.deleteById(id);

            return new ResponseEntity(HttpStatus.NO_CONTENT); //status 204 No Content

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //status 400 Bad Request
        }
    }

    @GetMapping
    public ResponseEntity find(@PageableDefault(page = 0, size = 10, sort = "id",
            direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email
//            @RequestParam(value = "documentNumber", required = false) String documentNumber,
//            @RequestParam(value = "phoneNumber", required = false) String phoneNumber
    ) {

        try {
            Customer filter = new Customer();
            filter.setId(id);
            filter.setName(name);
            filter.setEmail(email);
//            filter.setDocumentNumber(documentNumber);
//            filter.setPhoneNumber(phoneNumber);

            Page<Customer> entities = customerService.find(filter, pageable);
            List<CustomerDTO> dtos = customerConverterService.customerToDTOList(entities.getContent());

            return ResponseEntity.ok(dtos); //status 200 Ok

        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage()); //status 400 Bad Request
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id) {

        Optional<Customer> customerOptional = customerService.findById(id);

        if(!customerOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found."); //status 404 Not Found
        }
        return ResponseEntity.status(HttpStatus.OK).body(customerOptional.get()); //status 200 Ok
    }

}