package com.api.nataly.poc1api.presentation.controllers.exceptions;

public class MainAddressCannotBeUpdatedException extends RuntimeException {

    public MainAddressCannotBeUpdatedException() {
        super("Não foi possível concluir a ação, defina um endereço principal.");
    }
}
