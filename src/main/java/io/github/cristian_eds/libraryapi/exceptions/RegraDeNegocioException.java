package io.github.cristian_eds.libraryapi.exceptions;

import lombok.Getter;

public class RegraDeNegocioException extends RuntimeException {

    @Getter
    private String campo;

    public RegraDeNegocioException(String campo, String message) {
        super(message);
        this.campo = campo;
    }
}
