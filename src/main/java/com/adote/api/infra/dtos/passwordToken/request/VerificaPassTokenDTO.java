package com.adote.api.infra.dtos.passwordToken.request;

import jakarta.validation.constraints.NotBlank;

public record VerificaPassTokenDTO(
        @NotBlank(message = "O e-mail não pode ser vazio.") String email,
        @NotBlank(message = "O código de reset não pode ser vazio.") String token
) {
}
