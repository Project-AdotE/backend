package com.adote.api.core.exceptions.chavePix;

public class ChavePixNotFoundException extends RuntimeException {
    public ChavePixNotFoundException(String message) {
        super("Chave pix não encontrada com: " + message);
    }
}
