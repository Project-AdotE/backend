package com.adote.api.infra.dtos.chavePix.request;

public record ChavePixRequestDTO(
        String tipo,
        String chave,
        Long organizacao_id
) {
}
