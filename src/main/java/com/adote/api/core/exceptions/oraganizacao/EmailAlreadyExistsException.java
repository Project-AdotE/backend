package com.adote.api.core.exceptions.oraganizacao;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super("O email " + message + " já foi registrado");
    }
}
