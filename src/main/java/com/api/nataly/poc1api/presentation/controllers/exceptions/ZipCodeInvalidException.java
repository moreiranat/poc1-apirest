package com.api.nataly.poc1api.presentation.controllers.exceptions;

public class ZipCodeInvalidException extends RuntimeException {
    public ZipCodeInvalidException(String cep) {
        super("O CEP informado (" + cep + ") não é válido, digite novamente!");
    }
}
