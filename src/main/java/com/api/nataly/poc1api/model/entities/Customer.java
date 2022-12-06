package com.api.nataly.poc1api.model.entities;

import com.api.nataly.poc1api.model.enums.PersonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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

    @Column(name = "CUSTOMER_DOCUMENT_NUMBER", nullable = false)
    private String documentNumber;

    @Column(name = "CUSTOMER_PERSON_TYPE", nullable = false)
    private PersonType personType;

    @Column(name = "CUSTOMER_PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    private Set<Address> adresses; //ver vantagem de usar o Set ao invés do List. Ver se é melhor usar o Set mesmo
}
