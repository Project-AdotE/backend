package com.adote.api.infra.dtos.formulario.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MensagemRecusaDTO(
        @NotBlank(message = "Mensagem não pode ser numa")
        @Size(max = 300, message = "A mensagem deve ter no máximo 300 caracteres")
        String mensagem
) {
}
