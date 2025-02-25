package com.adote.api.infra.dtos.organizacao.request;

public record LoginRequestDTO(
        String email,
        String senha) {
}
