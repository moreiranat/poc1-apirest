package com.api.nataly.poc1api.presentation.dtos;

import com.api.nataly.poc1api.model.enums.PersonType;
import com.api.nataly.poc1api.model.groups.CnpjGroup;
import com.api.nataly.poc1api.model.groups.CpfGroup;
import com.api.nataly.poc1api.model.groups.CustomerGroupSequenceProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

@Data
@NoArgsConstructor
@AllArgsConstructor
@GroupSequenceProvider(CustomerGroupSequenceProvider.class)
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
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    @Pattern(regexp = "(^\\d{3}.\\d{3}.\\d{3}-\\d{2}$)|(^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$)",
            message = "Digite um CPF/CNPJ com formato válido (com pontos e espaços)!")
    private String documentNumber;

    @NotNull(message = "É obrigatório informar se o cliente é Pessoa Física ou Pessoa Jurídica!")
    private PersonType personType;

    @NotBlank(message = "É obrigatório informar o número de telefone do cliente!")
    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    private String phoneNumber;

}
