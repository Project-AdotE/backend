package com.adote.api.infra.dtos.organizacao.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequestDTO(

        @Email(message = "Formato de e-mail inválido")
        @NotNull(message = "Email deve ser preenchido")
        String email,

        @NotNull(message = "Senha deve ser preenchido")
        String senha) {
}
