package com.api.nataly.poc1api.presentation.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    @NotNull(message = "É obrigatório informar se é o endereço principal ou não!")
    private Boolean mainAddress = false;

    @NotNull(message = "É obrigatório informar o id do Cliente referente a esse endereço!")
    private Long customerId;



}
