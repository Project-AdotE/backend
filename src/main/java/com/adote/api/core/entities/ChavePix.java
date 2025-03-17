package com.adote.api.core.entities;

public record ChavePix(
        Long id,
        String tipo,
        String chave,
        Organizacao organizacao
) {
}
