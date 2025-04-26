package com.adote.api.infra.dtos.perguntas;

import com.adote.api.core.Enums.TipoPerguntaEnum;

public record PerguntaResponseDTO(
        Long id,
        String pergunta,
        Long posicao,
        TipoPerguntaEnum tipo
) {
}
