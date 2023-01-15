package com.api.nataly.poc1api.presentation.controllers.exceptions;

public class DeleteMainAddressException extends RuntimeException {

    public DeleteMainAddressException() {
        super("Não foi possível concluir a ação, pois o endereço principal não pode ser deletado.");
    }
}
