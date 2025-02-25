package com.adote.api.infra.dtos.organizacao.response;

public record OrganizacaoResponseDTO(
        Long id,
        String nome,
        String numero,
        String cnpj,
        String cep,
        String email) {
}
