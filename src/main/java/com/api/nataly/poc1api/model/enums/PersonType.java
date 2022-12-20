package com.api.nataly.poc1api.model.enums;

import com.api.nataly.poc1api.model.groups.CnpjGroup;
import com.api.nataly.poc1api.model.groups.CpfGroup;

public enum PersonType {

    FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class),
    JURIDICA("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class);

    private final String description;
    private final String document;
    private final String mask;
    private final Class<?> group;


    private PersonType(String description, String document, String mask, Class<?> group) {
        this.description = description;
        this.document = document;
        this.mask = mask;
        this.group = group;
    }

    public String getDescription(){
        return description;
    }

    public String getDocument(){
        return document;
    }

    public String getMask(){
        return mask;
    }

    public Class<?> getGroup(){
        return group;
    }
}
