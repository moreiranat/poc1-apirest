package com.api.nataly.poc1api.presentation.controllers.exceptions;

public class MissingFieldException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MissingFieldException(String fieldName) {
        super("The action could not be completed because the " + fieldName + " field is missing.");
    }
    public MissingFieldException(String fieldName, String typeOfRequest) {
        super("The " + typeOfRequest + " request could not be completed because the " + fieldName + " field is missing.");
    }
}
