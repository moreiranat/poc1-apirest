package com.api.nataly.poc1api.presentation.dtos;

import com.api.nataly.poc1api.model.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;

    private String name;

    private String email;

    private String documentNumber;

    private PersonType personType;

    private String phoneNumber;

}
