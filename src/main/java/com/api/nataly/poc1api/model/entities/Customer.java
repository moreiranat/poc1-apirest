package com.api.nataly.poc1api.model.entities;

import com.api.nataly.poc1api.model.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_CUSTOMER")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Long id;

    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String name;

    @Column(name = "CUSTOMER_EMAIL", nullable = false)
    private String email;

    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    @Column(name = "CUSTOMER_DOCUMENT_NUMBER", nullable = false, unique = true)
    private String documentNumber; //ver anotação para validacao de CPF e de CNPJ

    @Enumerated(EnumType.STRING)
    @Column(name = "CUSTOMER_PERSON_TYPE", nullable = false)
    private PersonType personType;

    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    @Column(name = "CUSTOMER_PHONE_NUMBER", nullable = false, unique = true)
    private String phoneNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL) //um Cliente pode ter varios Enderecos
    private Set<Address> adresses; //ver vantagem de usar o Set ao invés do List. Ver se é melhor usar o Set mesmo
    //Lazy: carregado do banco apenas quando de fato necessário
}
