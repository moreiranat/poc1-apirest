package com.api.nataly.poc1api.presentation.controllers.exceptions;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String object, String fieldName, Object field) {
        super("Could not find a " + object + " with " + fieldName + " " + field);
    }

}
