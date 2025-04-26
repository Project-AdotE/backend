package com.adote.api.core.entities;

public record Pergunta(
        Long id,
        String pergunta,
        Long posicao,
        String tipo
) {
}
