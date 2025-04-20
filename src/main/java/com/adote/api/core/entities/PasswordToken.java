package com.adote.api.core.entities;

import java.time.LocalDateTime;

public record PasswordToken(
        Long id,
        String email,
        String token,
        LocalDateTime expirationTime,
        Boolean used
) {
    public PasswordToken(String email, String token, LocalDateTime expirationTime) {
        this(null, email, token, expirationTime, false);
    }
}
