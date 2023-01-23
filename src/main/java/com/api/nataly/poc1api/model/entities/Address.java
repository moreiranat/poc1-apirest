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

    @Column(name = "ADDRESS_CEP", nullable = false)
    private String cep;

    @Column(name = "ADDRESS_LOGRADOURO", nullable = false)
    private String logradouro;

    @Column(name = "ADDRESS_COMPLEMENTO", nullable = false)
    private String complemento;

    @Column(name = "ADDRESS_BAIRRO", nullable = false)
    private String bairro;

    @Column(name = "ADDRESS_LOCALIDADE", nullable = false)
    private String localidade;

    @Column(name = "ADDRESS_UF", nullable = false)
    private String uf;

    @Column(name = "MAIN_ADDRESS")
    //@JsonIgnore
    private Boolean mainAddress;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) //Muitos Enderecos podem estar associados a 1 Cliente
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
    //Lazy: carregado do banco apenas quando de fato necessário

}
