package com.api.nataly.poc1api.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;

    private String street;

    private String number;

    private String neighborhood;

    private String city;

    private String cep;

    private String state;

    private Long customerId;

}
