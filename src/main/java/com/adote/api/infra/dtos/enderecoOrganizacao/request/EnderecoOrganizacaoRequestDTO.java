package com.adote.api.infra.dtos.enderecoOrganizacao.request;

public record EnderecoOrganizacaoRequestDTO(
        String cep,
        String rua,
        String numero,
        String cidade,
        String estado
) {
}
