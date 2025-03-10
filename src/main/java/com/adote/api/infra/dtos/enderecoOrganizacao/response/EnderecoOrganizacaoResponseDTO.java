package com.adote.api.infra.dtos.enderecoOrganizacao.response;

public record EnderecoOrganizacaoResponseDTO(
        Long id,
        String cep,
        String rua,
        String numero,
        String cidade,
        String estado
) {
}
