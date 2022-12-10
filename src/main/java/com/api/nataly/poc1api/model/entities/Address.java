package com.api.nataly.poc1api.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
//@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_ADDRESS")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Long id;

    @Column(name = "ADDRESS_STREET", nullable = false)
    private String street;

    @Column(name = "ADDRESS_NUMBER", nullable = false)
    private String number;

    @Column(name = "ADDRESS_NEIGHBORHOOD", nullable = false)
    private String neighborhood;

    @Column(name = "ADDRESS_CITY", nullable = false)
    private String city;

    @Column(name = "ADDRESS_CEP", nullable = false)
    private String cep;

    @Column(name = "ADDRESS_STATE", nullable = false)
    private String state;

    @Column(name = "MAIN_ADDRESS")
    @JsonIgnore
    private Boolean mainAddress;

    @ManyToOne(fetch = FetchType.LAZY) //Muitos Enderecos podem estar associados a 1 Cliente
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
    //Lazy: carregado do banco apenas quando de fato necessário

}
