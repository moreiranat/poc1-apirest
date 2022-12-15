package com.api.nataly.poc1api.presentation.controllers.exceptions;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String message) {
        super(message);
    }
}
