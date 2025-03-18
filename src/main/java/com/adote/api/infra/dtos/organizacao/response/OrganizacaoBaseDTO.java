package com.adote.api.infra.dtos.organizacao.response;

import com.adote.api.infra.dtos.enderecoOrganizacao.response.EnderecoResponseDTO;

public record OrganizacaoBaseDTO(
        Long id,
        String nome,
        String numero,
        String cnpj,
        EnderecoResponseDTO endereco,
        String email) {
}
