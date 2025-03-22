package com.adote.api.core.exceptions.oraganizacao;

public class OrganizacaoNotFoundException extends RuntimeException {
    public OrganizacaoNotFoundException(String message) {
        super("Organização " + message + " não encontrada");
    }
}
