package com.api.nataly.poc1api.presentation.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;

    @NotBlank(message = "É obrigatório informar o CEP!")
    @Pattern(regexp = "[0-9]{5}-[0-9]{3}", message = "Digite um CEP com formato válido!")
    private String cep; //zipCode

    @NotBlank(message = "É obrigatório informar o logradouro!")
    private String logradouro; //street

    @NotBlank(message = "É obrigatório informar o complemento!")
    private String complemento; //complement

    @NotBlank(message = "É obrigatório informar o bairro!")
    private String bairro; //neighborhood

    @NotBlank(message = "É obrigatório informar a localidade!")
    private String localidade; //city

    @NotBlank(message = "É obrigatório informar a UF!")
    private String uf; //state

    private Boolean mainAddress = false;

    private Long customerId;



}
