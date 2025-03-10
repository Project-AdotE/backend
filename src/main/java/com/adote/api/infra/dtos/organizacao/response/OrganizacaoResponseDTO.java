package com.adote.api.infra.dtos.organizacao.response;

import com.adote.api.infra.dtos.enderecoOrganizacao.response.EnderecoOrganizacaoResponseDTO;

public record OrganizacaoResponseDTO(
        Long id,
        String nome,
        String numero,
        String cnpj,
        EnderecoOrganizacaoResponseDTO endereco,
        String email) {
}
