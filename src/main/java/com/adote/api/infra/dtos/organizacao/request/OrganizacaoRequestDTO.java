package com.adote.api.infra.dtos.organizacao.request;

public record OrganizacaoRequestDTO(
        String nome,
        String numero,
        String cnpj,
        String cep,
        String email,
        String senha) {
}
