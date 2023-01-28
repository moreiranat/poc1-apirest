package com.api.nataly.poc1api.model.entities;

import com.api.nataly.poc1api.model.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "CUSTOMER_EMAIL", nullable = false, unique = true)
    private String email;

    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    @Column(name = "CUSTOMER_DOCUMENT_NUMBER", nullable = false, unique = true)
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "CUSTOMER_PERSON_TYPE", nullable = false)
    private PersonType personType;

    @Getter(onMethod = @__({@JsonIgnore}))
    @Setter(onMethod = @__({@JsonProperty}))
    @Column(name = "CUSTOMER_PHONE_NUMBER", nullable = false, unique = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL) //um Cliente pode ter varios Enderecos
    private List<Address> addresses = new ArrayList<>();
    //Lazy: carregado do banco apenas quando de fato necessário
    //Lazy: na hora que vai carregar um Customer no banco de dados, ele vai carregar só depois um endereço principal, para nao sobrecarregar o banco de dados
    //Lazy: busca lenta, permite que a busca de um relacionamento seja adiada ate que seja acessada
    //Por padrao, o OneToOne e ManyToMany são EAGER

}






