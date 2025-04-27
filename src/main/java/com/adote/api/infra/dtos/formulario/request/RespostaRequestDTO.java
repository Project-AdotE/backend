package com.adote.api.infra.dtos.formulario.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RespostaRequestDTO(

        @NotNull
        Long idPergunta,

        @NotBlank
        @Size(min = 1, max = 100)
        String resposta
) {
}
