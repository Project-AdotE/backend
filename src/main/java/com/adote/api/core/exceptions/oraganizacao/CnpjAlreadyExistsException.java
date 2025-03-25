package com.adote.api.core.exceptions.oraganizacao;

public class CnpjAlreadyExistsException extends RuntimeException {
    public CnpjAlreadyExistsException(String message) {
        super("O cnpj " + message + " já está registrado.");
    }
}
