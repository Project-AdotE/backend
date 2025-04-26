package com.adote.api.core.entities;

public record Respostas(
        Long id,
        Formulario formulario,
        Pergunta pergunta,
        String resposta
) {
}
