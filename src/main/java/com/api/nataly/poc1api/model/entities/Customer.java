package com.api.nataly.poc1api.model.entities;

import com.api.nataly.poc1api.model.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity
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

    //Lazy: na hora que vai carregar um Customer no banco de dados, ele vai carregar só depois um endereço principal, para nao sobrecarregar o banco de dados
    //Lazy: busca lenta, permite que a busca de um relacionamento seja adiada ate que seja acessada
    //Por padrao, o OneToOne e ManyToMany são EAGER
    @OneToOne(fetch = FetchType.LAZY) //relacionamento unidirecional
    //@Embedded
    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    @JoinColumn(name = "MAIN_ADDRESS", nullable = false, unique = true) //@JoinColumn indica o nome da coluna referenciada na chave estrangeira
    private Address mainAddress;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL) //um Cliente pode ter varios Enderecos
    private List<Address> adresses = new ArrayList<>();
    //Lazy: carregado do banco apenas quando de fato necessário
}
