package com.adote.api.core.entities;

public record EnderecoOrganizacao(
        Long id,
        String cep,
        String rua,
        String numero,
        String cidade,
        String estado
) {
}
