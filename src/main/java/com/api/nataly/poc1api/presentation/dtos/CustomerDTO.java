package com.api.nataly.poc1api.presentation.dtos;

import com.api.nataly.poc1api.model.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "É obrigatório informar o nome do cliente!")
    @Size(min = 2, max = 50, message = "Nome inválido! O nome deve possuir no mínimo 2 e no máximo 50 caracteres!")
    private String name;

    @NotBlank(message = "É obrigatório informar o e-mail do cliente!")
    @Email(message = "E-mail inválido! Digite um e-mail padrão válido: _@_._")
    private String email;

    @NotBlank(message = "É obrigatório informar o CPF/CNPJ do cliente!")
    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    private String documentNumber;

    @NotNull(message = "É obrigatório informar se o cliente é Pessoa Física ou Pessoa Jurídica!")
    private PersonType personType;

    @NotBlank(message = "É obrigatório informar o número de telefone do cliente!")
    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    private String phoneNumber;

}
