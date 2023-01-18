package com.api.nataly.poc1api.presentation.controllers.exceptions;

public class MaximumAddressLimitExceededException extends RuntimeException {
    public MaximumAddressLimitExceededException(Long customerId) {
        super("O Cliente de id " + customerId + " atingiu o limite máximo de endereços cadastrados.");
    }
}
