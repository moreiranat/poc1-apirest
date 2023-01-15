package com.api.nataly.poc1api.presentation.controllers.exceptions;

public class MaximumAddressLimitExceededException extends RuntimeException {
    public MaximumAddressLimitExceededException(Long id) {
        super("O Cliente de id " + id + " atingiu o limite máximo de endereços cadastrados.");
    }
}
