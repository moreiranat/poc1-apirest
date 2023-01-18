package com.api.nataly.poc1api.presentation.controllers.exceptions;

public class MainAddressCannotBeDeletedException extends RuntimeException {

    public MainAddressCannotBeDeletedException() {
        super("Não foi possível concluir a ação, pois este é o seu endereço principal e ele não pode ser deletado.");
    }
}
