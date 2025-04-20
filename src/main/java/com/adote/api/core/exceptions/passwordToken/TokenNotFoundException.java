package com.adote.api.core.exceptions.passwordToken;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException() {
        super("Token inválido ou expirado.");
    }
}
