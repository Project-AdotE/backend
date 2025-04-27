package com.adote.api.infra.dtos.formulario.response;

public record RespostaResponseDTO(
        Long id,
        String pergunta,
        String resposta
) {
}
